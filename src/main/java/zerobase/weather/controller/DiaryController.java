package zerobase.weather.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

@RestController
public class DiaryController {


	private final DiaryService diaryService;

	public DiaryController(DiaryService diaryService) {
		this.diaryService = diaryService;
	}

	@ApiOperation(value = "해당 날짜의 일기를 DB에 저장합니다.")
	@PostMapping("/create/diary")
	void createDiary(
		@RequestParam
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		@ApiParam(value = "일기를 저장할 날짜", example = "2023-01-01") LocalDate date,
		@RequestBody
		@ApiParam(value = "일가 내용",example = "나는 오늘 밥을 먹었다.") String text) {

		diaryService.createDiary(date, text);
	}

	@ApiOperation("선택한 날짜의 모든 일기를 가져 옵니다.")
	@GetMapping("/read/diary")
	List<Diary> readDiary(
		@RequestParam
		@DateTimeFormat(iso = ISO.DATE)
		@ApiParam(value = "조회할 날짜", example = "2023-01-01") LocalDate date) {

		return diaryService.readDiary(date);
	}

	@ApiOperation("선택한 날짜 범위의 모든 일기를 가져 옵니다.")
	@GetMapping("/read/diaries")
	List<Diary> readDiaries(
		@RequestParam
		@DateTimeFormat(iso = ISO.DATE)
		@ApiParam(value = "조회활 기간의 첫 날", example = "2023-01-01") LocalDate startDate,
		@RequestParam
		@DateTimeFormat(iso = ISO.DATE)
		@ApiParam(value = "조회할 기간의 마지막 날", example = "2023-01-01") LocalDate endDate) {

		return diaryService.readDiaries(startDate, endDate);
	}

	@ApiOperation("선택한 날짜의 일기를 수정합니다.")
	@PutMapping("/update/diary")
	void updateDiary(
		@RequestParam
		@DateTimeFormat(iso = ISO.DATE)
		@ApiParam(value = "수정할 날짜", example = "2023-01-01") LocalDate date,
		@RequestBody
		@ApiParam(value = "일기 내용", example = "나는 오늘 밥 대신 치킨을 먹었다.") String text) {

		diaryService.updateDiary(date, text);
	}

	@ApiOperation("선택한 날짜의 일기를 삭제 합니다.")
	@DeleteMapping("/delete/diary")
	void deleteDiary(
		@RequestParam
		@DateTimeFormat(iso = ISO.DATE)
		@ApiParam(value = "삭제할 일기의 날짜", example = "2023-01-01") LocalDate date) {

		diaryService.deleteDiary(date);
	}
}
