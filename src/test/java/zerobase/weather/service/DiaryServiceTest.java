package zerobase.weather.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.repository.DateWeatherRepository;
import zerobase.weather.repository.DiaryRepository;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

	@InjectMocks
	private DiaryService diaryService;

	@Mock
	private DiaryRepository diaryRepository;

	@Mock
	private DateWeatherRepository dateWeatherRepository;

	@Test
	@DisplayName("createDiary 기능")
	void createDiary() {
		//given
		diaryService.setApikey("f71f9c345a0cb5607c3b1b3b70cb24d7");
		Diary myDiary1 = getDiary(LocalDate.parse("2023-01-08"));
		when(diaryRepository.save(any())).thenReturn(myDiary1);

		String text = myDiary1.getText();
		LocalDate date = LocalDate.parse("2023-01-08");

		//when
		diaryService.createDiary(date, text);

		//then
		//createDiary를 해도 실제 db에 저장되지 않기 때문에 가져오면 null이 반환.
	}

	@Test
	@DisplayName("updateDiary 기능")
	void updateDiary() {

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