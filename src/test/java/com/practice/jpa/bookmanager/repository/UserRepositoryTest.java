package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void crud() { // create, read, update, delete
        /*FIND = SELECT */
//        List<User> users = userRepository.findAll();
//        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC,"name"));
//        List<User> users = userRepository.findAllById(Lists.newArrayList(1L,3L,5L));
//        List<User> users = userRepository.findAllById(Lists.newArrayList(1L,3L,5L));
//        users.forEach(System.out::println);

        /*SAVE = INSERT*/
//        User user1 = new User("jack","jack@fast.com");
//        User user2 = new User("steve","steve@fast.com");
//        userRepository.saveAll(Lists.newArrayList(user1,user2));
//        userRepository.save(user1);

        /*GET*/
//        User user = userRepository.getOne(1L); //여기서 중요한건 getOne는 Lazy한 패치를 지원하고 있다.
//        User user = userRepository.findById(1L).orElse(null); //orElse는 "결과값이 없으면 널로 보여줘" 라는 뜻이다.

//        userRepository.save(new User("new martin","newmartin@fast.com"));
//        userRepository.flush(); // flush는 쿼리에 영향을 가지 않는데 다음에 배울 영속성 파트에서 더 자세히 알아볼수 있다.

//        long count = userRepository.count();

        /*DLELTE*/
        // entity를 사용하여 지우는 쿼리
//        userRepository.delete(userRepository.findById(1L).orElseThrow(RuntimeException::new)); // delete는 Null이면안됨.

        // ID를 사용하여 지우기
//        userRepository.deleteById(1L);

        // ID를 사용하여 여러 컬럼 지우기
//        userRepository.deleteAllById(Lists.newArrayList(1L,3L));

        // 다 지우기 (엔티티개수만큼 쿼리를 떄림)
//        userRepository.deleteAll();

        // 모든 엔티티 지우기
//        userRepository.deleteAllInBatch();

        // ID를 사용하여 여러 컬럼 지우기
//        userRepository.deleteAllInBatch(userRepository.findAllById(Lists.newArrayList(1L,3L)));
//        userRepository.findAll().forEach(System.out::println);

//        // paging !!! 사용
//        Page<User> users = userRepository.findAll(PageRequest.of(1,3)); // page는 0페이지 부터 시작한다.
//
//        System.out.println(" 페이징 정보 : " + users);
//        System.out.println(" Size(한 페이지에 들어갈 컬럼 수) : 한 페이지에 " + users.getSize() + "개의 게시물이 들어가도록 해뒀다.");
//        System.out.println(" totalElements(총 컬럼 수) : 총 " + users.getTotalElements() + "개의 게시물이 있다.");
//        System.out.println(" totalPages(전체 페이지 수) : 총 " + users.getTotalPages() + "페이지가 있다.");
//        System.out.println(" Number(현재 페이지) : 지금 선택된 페이지는 " + users.getNumber() + "페이지 이다.");
//        System.out.println(" numberOfElements(내가 선택한 페이지의 컬럼개수) : 현재 이 페이지에는 " + users.getNumberOfElements() + "개의 게시물이 있다.");
//        System.out.println(" Sort(정렬 정보) : " + users.getSort() + "정렬 되어있다.");
//        users.getContent().forEach(System.out::println); // 현재 선택한 페이지의 컬럼들.

        /*exampleMatcher*/

//        ExampleMatcher matcher = ExampleMatcher.matching()                                  //
//                .withIgnorePaths("name")                                                    // name은 매칭 하지 않겠다.
//                .withMatcher("email",endsWith());                                // email에 대해서 확인하겠다. 끝에있다는걸
//        Example<User> example = Example.of(new User("m","fast.com"),matcher);  //
        User user = new User();
        user.setEmail("slow");

        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("email",contains());
        Example<User> example = Example.of(user, matcher);


        userRepository.findAll(example).forEach(System.out::println);

    }
}