package zerobase.weather.repository;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

@SpringBootTest
@Transactional
class DiaryRepositoryTest {

	@Autowired
	DiaryRepository diaryRepository;

	@Autowired
	DiaryService diaryService;

	@Test
	@DisplayName("readDiary 기능")
	void findAllByDate() {
		//given
		LocalDate date = LocalDate.parse("2023-01-08");
		Diary myDiary = getDiary(date);
		diaryRepository.save(myDiary);

		//when
		List<Diary> list = diaryRepository.findAllByDate(date);
		Diary savedDiary = list.get(0);

		//then
		Assertions.assertEquals(myDiary.getDate(), savedDiary.getDate());
		Assertions.assertEquals(myDiary.getText(), savedDiary.getText());
		Assertions.assertEquals(myDiary.getId(), savedDiary.getId());
		Assertions.assertEquals(myDiary.getTemperature(), savedDiary.getTemperature());
		Assertions.assertEquals(myDiary.getIcon(), savedDiary.getIcon());
		Assertions.assertEquals(myDiary.getWeather(), savedDiary.getWeather());
	}

	@Test
	@DisplayName("readDiaries 기능")
	void findAllByDateBetween() {
		//given
		LocalDate date1 = LocalDate.parse("2022-03-01");
		LocalDate date2 = LocalDate.parse("2022-03-02");
		LocalDate date3 = LocalDate.parse("2022-03-03");

		Diary myDiary1 = getDiary(date1);
		Diary myDiary2 = getDiary(date2);
		Diary myDiary3 = getDiary(date3);

		diaryRepository.save(myDiary1);
		diaryRepository.save(myDiary2);
		diaryRepository.save(myDiary3);

		//then
		//given에 있던 값이 아닌 DB에 원래 있던 값이 나올 수도 있음.
		List<Diary> list = diaryRepository.findAllByDateBetweenOrderByDate(date1, date3);

		//when
		Assertions.assertEquals(myDiary1, list.get(0));
		Assertions.assertEquals(myDiary2, list.get(1));
		Assertions.assertEquals(myDiary3, list.get(2));
	}


	@Test
	@DisplayName("deleteDiary 기능")
	void deleteAllByDate() {
		//given
		LocalDate date = LocalDate.parse("2022-03-01");
		Diary myDiary1 = getDiary(date);
		Diary myDiary2 = getDiary(date);
		diaryRepository.save(myDiary1);
		diaryRepository.save(myDiary2);

		//when
		diaryRepository.deleteAllByDate(date);
		List<Diary> list = diaryRepository.findAllByDate(date);

		//then
		Assertions.assertEquals(0, list.size());
	}

	@Test
	@DisplayName("updateDiary 기능")
	void updateDiary() {
		//given
		String textToChange = "나는 치킨을 먹었다.";
		LocalDate date = LocalDate.parse("2022-03-01");
		Diary myDiary = getDiary(date);
		diaryRepository.save(myDiary);

		//when
		diaryService.updateDiary(date, textToChange);
		Diary getDiary = diaryRepository.getFirstByDate(date);

		//then
		Assertions.assertEquals(textToChange, getDiary.getText());
	}

	Diary getDiary(LocalDate date) {

		Diary diary = new Diary();
		DateWeather dateWeather = new DateWeather();

		dateWeather.setDate(date);
		dateWeather.setWeather("Haze");
		dateWeather.setIcon("50n");
		dateWeather.setTemperature(kToC(278.23));

		diary.setDateWeather(dateWeather);
		diary.setText("나는 밥을 먹었다.");

		return diary;
	}

	double kToC(Double k) {
		return Math.round((k - 273.15) * 100) / 100.0;
	}
}