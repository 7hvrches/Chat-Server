# websocket을 이용한 채팅 서버 프로젝트입니다.

### 개발 환경
- Spring boot 3.x
- Java 17
- Websocket
- Stomp
- Redis pub/sub
- bootstrap 4.5.2
- vus.js 2.6.14
- axios 0.17.1
- IDE Intellij

### 구현
Pub/Sub(Publish/Subscribe) 패턴 사용</br>
발행자(Publisher)는 특정 주제에 대한 채널을 발행하는 메시지를 생성하고 메시지 브로커에게 전달하면 메시지 브로커가 해당 채널을 구독하는
구독자(Subscriber)에게 메시지를 전달</br>
</br>
Publisher (발행자):</br>
Redis에서 메시지를 생성하고 특정 채널에 해당하는 메시지를 발행합니다.</br>
Message Broker (Redis 서버):</br>
Redis 서버가 메시지를 받아 해당 채널을 구독하는 모든 구독자들에게 메시지를 전달합니다.</br>
Subscriber (구독자):</br>
Redis에서 특정 채널을 구독한 클라이언트는 새로운 메시지를 수신하고 처리합니다.</br>
![](../../Users/JungDoHyeon/Desktop/redis-publish-subscriber.png)
(출처: https://www.geeksforgeeks.org/redis-publish-subscribe/)</br></br>
<b>자세한 로직은 소스코드 주석 참고</b></br>


### 참고 자료
- https://github.com/codej99/websocket-chat-server/tree/feature/developchatroom
- https://brunch.co.kr/@springboot/695
- https://tecoble.techcourse.co.kr/post/2022-10-04-active_profiles/
- https://doosicee.tistory.com/entry/Redis%EB%A5%BC-SpringBoot%EC%97%90-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0
- https://sunghs.tistory.com/90   
- https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/in-memory.html etc...
