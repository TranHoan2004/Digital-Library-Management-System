## Lưu ý trước khi code

Vì là đang chạy CI nên khi ae tạo project mới, cần khai báo tên project vào .github/workflow/java-ci/yml, dòng 21 (matrix.service) để github nó tích hợp. Phải đúng tên thì mới tích hợp được

Ví dụ:
```yml
jobs:
  spring-boot-build:
    runs-on: ubuntu-latest
    strategy:
      fail-fast: false
      matrix:
        service: [
          EurekaServer, # Dự án EurekaServer, nằm ở src/EurekaServer
          APIGateway, # Dự án APIGateway, nằm ở src/APIGatway
          book-service,
          member-service,
          loan-service,
          fine-service,
          notification-service
        ]
```