package com.ff1451.movie_review.dto.movie;

import java.util.List;

public record MovieDetailInfoApiResponse(
    MovieInfoResult movieInfoResult
) {
    public record MovieInfoResult(
        MovieInfo movieInfo,
        String source
    ) {}

    public record MovieInfo(
        String movieCd,
        String movieNm,
        String movieNmEn,
        String movieNmOg,
        String showTm,
        String prdtYear,
        String openDt,
        String prdtStatNm,
        String typeNm,
        List<Nation> nations,
        List<Genre> genres,
        List<Director> directors,
        List<Actor> actors,
        List<ShowType> showTypes,
        List<Company> companys,
        List<Audit> audits,
        List<Staff> staffs
    ) {}

    public record Nation(String nationNm) {}
    public record Genre(String genreNm) {}
    public record Director(String peopleNm, String peopleNmEn) {}
    public record Actor(String peopleNm, String peopleNmEn, String cast, String castEn) {}
    public record ShowType(String showTypeGroupNm, String showTypeNm) {}
    public record Company(String companyCd, String companyNm, String companyNmEn, String companyPartNm) {}
    public record Audit(String auditNo, String watchGradeNm) {}
    public record Staff(String peopleNm, String peopleNmEn, String staffRoleNm) {}
}
