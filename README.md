# 사전캠프 과제 수행 내역 정리

## 상품 CRUD

### 상품 등록

![product_insert.png](/docs/images/product_insert.png)

### 상품 조회(단건)

![product_select_one.png](/docs/images/product_select_one.png)

### 상품 조회(리스트)

![product_select_list.png](/docs/images/product_select_list.png)

### 상품 수정

![product_select_update.png](/docs/images/product_update.png)

### 상품 삭제

![product_select_delete.png](/docs/images/product_delete.png)

---

## 주문 생성

### 재고 차감 기능 적용 전

![order_create.png](/docs/images/order_create.png)

### 재고 차감 기능 적용

<details>

  <summary>전체 테스트 과정 정리</summary>

1. 재고 차감 테스트용 상품 추가(재고: 1)

    ![order_create_2.png](/docs/images/order_create_2.png)

2. 2명의 사용자가 동시에 상품을 주문
    * 동시 실행을 위해 다음의 명령어를 인텔리제이 git bash에 입력해서 실행
    * 동시에 주문을 진행할 인원이 2명뿐이므로, curl 명령어를 사용하여 간단하게 테스트
   
    ```bash
    curl -s -X POST http://localhost:8080/api/orders \
    -H "Content-Type: application/json" \
    -d '{"productId": 3, "quantity": 1}' \
    -w "\n[Request 1] Status: %{http_code}\n" & \
    curl -s -X POST http://localhost:8080/api/orders \
    -H "Content-Type: application/json" \
    -d '{"productId": 3, "quantity": 1}' \
    -w "\n[Request 2] Status: %{http_code}\n"
    ```

3. 1명만 주문 처리되는지 확인: 먼저 주문 성공하면 상태코드는 201, 늦게 주문해서 상품 재고가 없으면 상태코드는 409

    ![order_create_5.png](/docs/images/order_create_5.png)
4. 

</details>

* 실행 결과

![order_create_5.png](/docs/images/order_create_5.png)

---

## 주문 조회

### 주문 단건 조회

* 주문 내역 조회 시 주문한 상품명도 함께 조회

![order_select_one.png](/docs/images/order_select_one.png)

* 주문한 상품의 이름, 가격 변경 후 주문 내역 조회 시 변경된 정보를 반영

![order_select_one_2.png](/docs/images/order_select_one_2.png)
![order_select_one_3.png](/docs/images/order_select_one_3.png)

### 주문 목록 조회

* 주문 생성 1회 진행 후 주문 목록 조회
* Product 엔티티에 `@BatchSize(size = 20)`을 사용하여 주문 테이블에 조인된 상품의 데이터를 1개 쿼리로 일괄 조회(한번에 최대 20개까지 일괄 조회 가능)
* 조회된 주문 내역은 3개이지만, 주문 목록을 조회 시 쿼리는 총 2회(orders 조회 + 조인된 products 일괄 조회)만 실행 -> N+1 문제 해소

![order_select_list.png](/docs/images/order_select_list.png)
![order_select_list_2.png](/docs/images/order_select_list_2.png)

<details>

  <summary>추가 주문 생성 + 주문 목록 조회 쿼리 실행 로그</summary>

```
Hibernate: 
    insert 
    into
        orders
        (order_date, product_id, quantity) 
    values
        (?, ?, ?)
Hibernate: 
    select
        o1_0.id,
        o1_0.order_date,
        o1_0.product_id,
        o1_0.quantity 
    from
        orders o1_0 
    limit
        ?, ?
Hibernate: 
    select
        p1_0.id,
        p1_0.category,
        p1_0.created_at,
        p1_0.description,
        p1_0.is_existed,
        p1_0.name,
        p1_0.price,
        p1_0.stock,
        p1_0.updated_at 
    from
        products p1_0 
    where
        p1_0.id in (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
```

</details>