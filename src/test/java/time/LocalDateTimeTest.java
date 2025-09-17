package time;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

@Slf4j
public class LocalDateTimeTest {

    @Test
    void test_localDate() {

        //LocalDate YYYYMMDD 날짜를 표ㅗ현하는데 사용.
        //TimeZone 개념이 필요없는 날짜 정보를 나타내기 위해서 사용
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        System.out.println(LocalDate.of(2020, 1, 20));
    }

    @Test
    void test_localTime() {
        //LocalTime클래스는 시간을 표현하는데 사용
        //TimeZone개념이 필요없는 시간 정보를 나타내기 위해서 사용된다.
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime); //21:00:37.303776
    }

    @Test
    void test_localDateTime() {
        //날짜와 시간정보를 모두 표현
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
    }

    @Test
    void test_instant() {

        //Instant 클래스: 기계의 날짜와 시간

        //Instatnt클래스는 유닉스 에포크시간(1970년 1월 1일 0시 0분 0초 UTC)를 기준으로 특정 지점까지의 시간을 초로 표현
        System.out.println(Instant.ofEpochSecond(3));
        log.info("Instant.ofEpochSecond(3)={}", Instant.ofEpochSecond(3));
        log.info("Instant.now() = {}", Instant.now().getLong(ChronoField.INSTANT_SECONDS));

        ZonedDateTime zdtSeoul = Year.of(2002).atMonth(6).atDay(18).atTime(20, 30).atZone(ZoneId.of("Asia/Seoul"));
        System.out.println("Time in Seoul = " + zdtSeoul);

        Instant instant = zdtSeoul.toInstant();
        System.out.println("Instant = " + instant + ", Timestamp = " + instant.getEpochSecond());

        ZonedDateTime zdtVancouver = instant.atZone(ZoneId.of("America/Vancouver"));
// ZonedDateTime zdtVancouver = ZonedDateTime.ofInstant(instant, ZoneId.of("America/Vancouver")); 와 동일
        System.out.println("Tine in Vancouver = " + zdtVancouver);
        System.out.println(zdtVancouver.toInstant() + " " + zdtVancouver.toInstant().getEpochSecond());

    }


    @Test
    void test_duration_period() {

    }


    @Test
    void test_zonedDateTime() {

        ZoneId zoneId = ZoneId.systemDefault();

        System.out.println(ZonedDateTime.now());

        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        System.out.println(offsetDateTime);
    }


}
