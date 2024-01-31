# Shopping
__노션__ : https://www.notion.so/with-compose-b63b638778a542f08440ea7e14b0831f
쇼핑몰 클린 아키텍처

# 앱 기능 구현
1. Compose를 이용한 UI 구현
2. MVVM 아키텍쳐를 이용하여 비즈니스 로직과, UI로직을 분리하여 개발
3. 멀티모듈을 이용하여 좀 더 명확하게 분리하여 개발
4. Hilt를 이용하여 의존성 주입
5. Room을 이용하여 데이터 정보 저장
6. 구글 로그인, 카카오 로그인 연동


# 사용 스택
Coil, hilt, Room, Compose, MVVM, multiModule

# 앱 설명
+ 컴포즈를 사용하여 쇼핑몰 클론 코딩

# 기능

## 메인 화면
+ 상품들과 배너, 그리고 인기 상품들을 보여준다.

<img src ="https://github.com/Yoon-Chan/Shopping/assets/56026214/b07a4c6e-a391-49a0-8a18-f32467600758" width=300 height=600 />

## 카테고리 화면
+ 각 카테고리들을 보여주며 해당 카테고리 이동시 카테고리에 맞는 상품들을 가져온다.

<img src ="https://github.com/Yoon-Chan/Shopping/assets/56026214/efc485be-02b3-48b2-9a68-b639ba0cd991" width=300 height=600 />


## 좋아요 모아보기
+ 제품 목록 중에 하트를 누르면 관심 상품으로 등록할 수 있으며 관심 제품들을 따로 모아볼 수 있다.
<img src ="https://github.com/Yoon-Chan/Shopping/assets/56026214/ea8e7043-3f58-4c7f-b75f-567e67ed9bae" width=300 height=600 />
  
## 카카오 구글 소셜 로그인

<img src ="https://github.com/Yoon-Chan/Shopping/assets/56026214/7769c16f-3f21-45ca-90d7-248b0ed47a46" width=300 height=600 />

## 검색 기능
+ 검색 아이콘을 누르면 검색 화면으로 이동하며, 최근 검색 정보와 검색할수 있는 텍스트뷰가 나온다.
+ 검색을 하면 맞는 상품들이 나오며, 카테고리, 금액별 필터를 이용하여 상품들을 찾을 수 있도록 구현했다.
<img src ="https://github.com/Yoon-Chan/Shopping/assets/56026214/a70379db-80c6-4663-ac8a-a4ad44c6c609" width=300 height=600 />

## 제품 상세 및 장바구니 화면
+ 상품을 클릭하면 제품 상세로 이동하며, 앱 바의 카트 아이콘을 클릭하면 제품 상세에서 담은 삼품들의 결제화면으로 이동한다.
+ 장바구니 화면에서 결제를 누르면 결제된 상품들이 전부 사라지며, 결제한 내역은 마이페이지에 결제 내역을 통해 확인할 수 있다.

|제품 상세 화면| 장바구니 화면| 결제 내역 화면 |
|:--:|:--:|:--:|
|![product_detail](https://github.com/Yoon-Chan/Shopping/assets/56026214/55c62668-8610-4aee-8502-beb622f03531) | ![장바구니](https://github.com/Yoon-Chan/Shopping/assets/56026214/a6f47550-a631-4181-9f77-b5860f98e77c) | ![결제내역](https://github.com/Yoon-Chan/Shopping/assets/56026214/725b897a-bdb1-4bd6-90e0-1d6161dc9c94) |
