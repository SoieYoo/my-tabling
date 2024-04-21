# 매장 테이블 예약 서비스
식당이나 점포를 이용하기 전에 미리 예약을 하여 편하게 식당/점포를 이용할 수 있는 서비스

# 프로젝트 기능 및 설계

### [고객]

- 회원가입 기능
    - POST /register
    - 사용자는 회원가입을 할 수 있다. 일반적으로 모든 사용자는 회원가입시 USER 권한 (일반 권한)을 지닌다.
    - 회원가입시 이름, 이메일, 패스워드를 입력받으며, 이메일은 unique 해야한다.  

- 로그인 기능
    - POST /login
    - 사용자는 로그인을 할 수 있다. 로그인시 회원가입때 사용한 이메일과 패스워드가 일치해야한다.

- 매장 목록 조회 기능
    -  GET /stores 또는 /stores?searchTerm={searchKeyword}
    - 로그인하지 않은 사용자를 포함한 모든 사용자는 게시글을 조회할 수 있다.
    - 매장의 목록은 매장명의 오름차순으로 기본 정렬되며, 별점이 높은순/낮은순 으로도 정렬이 가능하다.
    - 매장 목록 조회 시 응답에는 매장명, 영업시간의 정보가 필요하다.
    - 매장 목록은 개수가 많을 수 있으므로 paging 처리를 한다.

- 특정 매장 상세 조회 기능
    - GET /stores/{storeId}
    - 로그인하지 않은 사용자를 포함한 모든 사용자는 매장을 조회할 수 있다.
    - 매장명, 매장 주소, 매장 소개, 영업 시간, 예약 가능한 시간이 조회된다.
      
- 예약 등록 기능
    - POST /reservation
    - 로그인한 사용자에 한해 예약을 등록할 수 있다.
    - 예약 시간, 매장 고유 ID (DB의 PK), 사용자 고유 ID (DB의 PK), 방문 인원수로 예약 등록이 가능하다.
    - 예약을 등록하면 [요청] 상태가 된다.
      
- 예약 취소 기능
    - PATCH reservation/cancel/{reservationId} -> /reservation/{reservationId}/cancel
    - 로그인한 사용자이고, 해당 예약이 존재하는 사용자에 한해 예약을 취소할 수 있다.
    - 예약 상태가 [완료]인 경우 예약을 취소할 수 없다. (완료 : 예약 승인 후 매장 방문까지 한 상태)
    - 예약 번호 로 예약을 취소할 수 있다.
    - 취소가 완료되면 예약 상태가 [취소]로 변경된다.
      
- 방문 확인 기능
    - POST /visit/check/{reservationId} -> /visit/{reservationId}/confirm
    - 존재하는 예약이고, 관리자의 승인을 받아 예약 상태가 [승인] 상태이고, 예약 시간과 방문 확인 API 호출 시간을 비교해 예약 시간 이후 10분 이내 방문 시 확인이 완료된다.
    - 방문 확인 후 예약 상태는 [완료]로 변경된다.
    
- 리뷰 등록 기능
    - POST /review
    - 로그인한 사용자이고, 예약 상태가 [완료]인 경우 리뷰 작성이 가능하다.
    - 사용자 고유 ID, 매장 ID, 별점(0.0~5.0), 내용(텍스트 및 사진)으로 등록이 가능하다.
      
- 리뷰 조회 기능
    - GET /review/{reviewId} -> /reviews/{reviewId}
    - 로그인하지 않은 사용자를 포함한 모든 사용자는 리뷰를 조회할 수 있다.
    - 매장명, 내용, 별점, 작성 시간이 조회된다.
      
- 리뷰 수정 기능
    - PUT /review/{reviewId} -> /reviews/{reviewId}
    - 로그인 한 사용자이고, 리뷰 작성자 본인에 한해 수정할 수 있다.
    - 내용, 별점을 수정할 수 있다.
      
- 리뷰 삭제
    - DELETE /review/{reviewId} -> /reviews/{reviewId}
    - 로그인 한 사용자이고, 리뷰 작성자 본인에 한해 삭제할 수 있다.
    - DB에서 삭제한다.

### [매장 관리자]

- 회원가입 기능
    - POST /register
    - 모든 관리자는 회원가입시 ADMIN 권한 (관리자 권한)을 지닌다.
    - 회원가입시 이름, 이메일, 패스워드를 입력받으며, 이메일은 unique 해야한다.
      
- 로그인 기능
    - POST /login
    - 관리자는 로그인을 할 수 있다. 로그인시 회원가입때 사용한 이메일과 패스워드가 일치해야한다.
      
- 매장 등록 기능
    - POST /store/register -> /store
    - 로그인한 관리자에 한해 매장을 등록할 수 있다.
    - 매장명, 매장 관리자 ID, 매장 주소, 매장 연락처, 개장 시간, 폐장 시간, 예약 시간 단위(예: 30분, 60분 또는 맞춤 설정) 을 저장한다.
      
- 매장 조회 기능
    - GET /stores
    - 로그인한 관리자에 한해 매장을 조회할 수 있다.
    - 매장의 목록은 매장명의 한글 및 영어 오름차순으로 기본 정렬되며, 등록일 최신순으로도 정렬이 가능하다.
    - 매장 목록 조회 시 응답에는 매장명, 매장 주소, 매장 연락처, 개장 시간, 폐장 시간, 예약 시간 단위 정보가 필요하다.
    - 매장 목록은 개수가 많을 수 있으므로 paging 처리를 한다.
      
- 매장 수정 기능
    - PUT /store/{storeId} -> /stores/{storeId}
    - 로그인한 관리자 중 매장 관리자 ID와 일치하는 경우 매장 수정이 가능하다.
    - 매장명, 매장 관리자 ID, 매장 주소, 매장 연락처, 개장 시간, 폐장 시간, 예약 시간 단위(예: 30분, 60분 또는 맞춤 설정) 를  수정할 수 있다.
      
- 매장 삭제 기능
    - DELETE /store/{storeId} -> /stores/{storeId}
    - 로그인한 관리자 중 매장 관리자 ID와 일치하는 경우 매장 삭제가 가능하다.
      
- 매장 관리자 수정 기능
    - POST /store/manager -> /stores/{storeId}/manager
    - 매장 엔티티의 매장 관리자를 변경할 수 있다.
    - 로그인 한 관리자 중 매장 관리자 ID와 일치하는 경우 매장 관리자 변경이 가능하다.
      
- 예약 승인 기능
    - PATCH /reservation/approve/{reservationId} -> /reservations/{reservationId}/approve
    - 로그인 한 관리자 중 해당 매장의 매장 관리자인 경우 예약 승인이 가능하다.
    - 승인이 되면 예약 상태가 [승인]으로 변경된다.
      
- 예약 거절 기능
    - PATCH /reservation/reject/{reservationId} -> /reservations/{reservationId}/reject
    - 로그인 한 관리자 중 해당 매장의 매장 관리자인 경우 예약 거절이 가능하다.
    - 승인이 되면 예약 상태가 [거절]으로 변경된다.
      
- 예약 조회 기능
    - GET /reservations/{storeId}
    - 로그인 한 관리자 중 해당 매장의 매장 관리자인 경우 예약 조회가 가능하다.
    - 예약 목록은 최신순으로 정렬된다.
    - 응답은 고객명, 예약시간, 인원수 정보이다.
      
- 예약 승인 시 메일 발송 기능
    - 관리자가 예약을 승인한 경우, 해당 고객에게 예약 정보를 메일로 발송한다.
    - 비동기로 구현한다.


## ERD (수정 전)
![tabling+](https://github.com/SoieYoo/my-tabling/assets/50662434/5ada963d-8658-49d8-afd7-b5d38f9ff69d)

## ERD (수정 후)
![tabling+ (1)](https://github.com/SoieYoo/my-tabling/assets/50662434/a34e1d47-df7b-4b1a-ab31-58b6f8036ab8)


## Trouble Shooting
https://www.rabbitmq.com

## Tech Stack
  <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white"><img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
  <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white">
  <img src="https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white">
  <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white">
