# LoginBoard

## 프로젝트 개요
LoginBoard는 가장 기본적인 기능인 글쓰기, 조회, 수정, 삭제의 CRUD 기능을 가진 게시판에 회원 로그인 기능을 추가한 프로젝트 입니다.
프로젝트는 SpringBoot + JPA + SpringSecurity + MySQL + Thymeleaf로 구현하였으며, 컨트롤러는 REST API를 이용하여 구현하였습니다.

## 주요 기능
- 게시글의 조회 및 작성, 수정, 삭제(REST API를 이용하여 CRUD기능 작성)

- 회원가입과 로그인 기능(SpringSecurity를 이용하여 로그인 인증 기능 구현)

- 인증된 사용자(로그인 유저)만이 게시글 작성이 가능하며, 조회는 비회원도 가능, 게시글의 수정 및 삭제는 작성자 본인과 어드민(관리자) 계정만 가능하도록 구현하였습니다.


## 구현 화면
![KakaoTalk_20200711_210126546](https://user-images.githubusercontent.com/51356655/87224340-8245be00-c3bf-11ea-8985-443ba8589c11.png)
![KakaoTalk_20200711_210157375](https://user-images.githubusercontent.com/51356655/87224343-82de5480-c3bf-11ea-922d-1b078f2f5d7d.png)
![KakaoTalk_20200711_210209609](https://user-images.githubusercontent.com/51356655/87224344-8376eb00-c3bf-11ea-856b-59db4cd2fd97.png)
![KakaoTalk_20200711_210237123](https://user-images.githubusercontent.com/51356655/87224346-840f8180-c3bf-11ea-9703-c40d6cd300a8.png)
![KakaoTalk_20200711_210256714](https://user-images.githubusercontent.com/51356655/87224348-840f8180-c3bf-11ea-9b23-cc0b61fbcb69.png)
![KakaoTalk_20200711_210359378](https://user-images.githubusercontent.com/51356655/87224349-84a81800-c3bf-11ea-94e8-51495b020f1f.png)
![KakaoTalk_20200711_210411048](https://user-images.githubusercontent.com/51356655/87224350-84a81800-c3bf-11ea-85e3-5d5d6f1da002.png)
![KakaoTalk_20200711_210428049](https://user-images.githubusercontent.com/51356655/87224352-8540ae80-c3bf-11ea-8325-1c49ef8d9c24.png)
![KakaoTalk_20200711_210453987](https://user-images.githubusercontent.com/51356655/87224353-8540ae80-c3bf-11ea-881d-2e56a9ea123d.png)
![KakaoTalk_20200711_210513841](https://user-images.githubusercontent.com/51356655/87224354-85d94500-c3bf-11ea-99eb-26afaeaf6d3a.png)
![KakaoTalk_20200711_210546729](https://user-images.githubusercontent.com/51356655/87224355-85d94500-c3bf-11ea-8e76-cd16e39d0128.png)
![KakaoTalk_20200711_210604542](https://user-images.githubusercontent.com/51356655/87224356-8671db80-c3bf-11ea-8f66-ef1f0af05865.png)

## 학습 내용
### SpringMVC
기본적인 SpringMVC의 어노테이션을 이용한 REST API의 작성법을 학습하였습니다.
- @GetMapping
- @PostMapping
- @PutMapping
- @PatchMapping
- @DeleteMapping
- @PathVariable
- @RequestBody 

### SpirngFramework 패키지
- @SpringSecurity를 이용한 회원가입 및 로그인 인증 방법
- @Pageable 어노테이션을 통한 데이터의 페이징 방법

### MySQL
- MySQL WorkBench 사용법(Reverse Engineering 등)
- 테이블간 연관 관계 설정

### JPA
- JpaRepository를 통한 DB 연동
- 연관관계 설정을 위한 어노테이션 사용법(@OneToMany, @ManyToOne 등)

### Java
- Validation을 이용한 유효성 검사
