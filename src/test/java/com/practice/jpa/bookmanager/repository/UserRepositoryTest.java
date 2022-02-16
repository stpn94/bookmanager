package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.Address;
import com.practice.jpa.bookmanager.domain.Gender;
import com.practice.jpa.bookmanager.domain.User;
import com.practice.jpa.bookmanager.domain.UserHistory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void embedTest() {
        userRepository.findAll().forEach(System.out::println);

        User user = new User();
        user.setName("steve");
        user.setHomeAddress(new Address("서울시", "강남구", "강남대로 364 미왕빌딩", "06241"));
        user.setCompanyAddress(new Address("서울시", "성동구", "성수이로 113 제강빌딩", "04794"));

        userRepository.save(user);

        User user1 = new User();
        user1.setName("joshua");
        user1.setHomeAddress(null);
        user1.setCompanyAddress(null);

        userRepository.save(user1);

        User user2 = new User();
        user2.setName("jordan");
        user2.setHomeAddress(new Address());
        user2.setCompanyAddress(new Address());

        userRepository.save(user2);
        entityManager.clear(); // 영속성 컨텍스트의 cache값을 지워주고 보자.
        userRepository.findAll().forEach(System.out::println);
        userHistoryRepository.findAll().forEach(System.out::println);

        userRepository.findAllRawRecord().forEach(a -> System.out.println(a.values()));
    }

    @Test
    void userRelationTest(){
        User user = new User();
        user.setName("david");
        user.setEmail("david@fast.com");
        user.setGender(Gender.MALE);

        userRepository.save(user);

        user.setName("danielll");
        userRepository.save(user);
        user.setEmail("daniel@fast.com");
        userRepository.save(user);

        userHistoryRepository.findAll().forEach(System.out::println);
        // Table 관계로 가져오기.
//        List<UserHistory> result = userHistoryRepository.findByUserId(userRepository.findByEmail("daniel@fast.com").getId());
        List<UserHistory> result = userRepository.findByEmail("daniel@fast.com").getUserHistories();

        result.forEach(System.out::println);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");

        //결론은 User와 UserHistory데이터를 모두 가져오기 위헤서는 N : 1을 사용하여 userHistory 즉 N쪽에서 getUser를 사용하여 검색한다.
        System.out.println("UserHistory.getUser() - (N : 1) : " + userHistoryRepository.findAll().get(0).getUser());
    }

    @Test
    void userHistoryTest() {
        User user = new User();
        user.setEmail("martin-new@fastcampus.com");
        user.setName("martin-new");

        userRepository.save(user);

        user.setName("martin-new-new");

        userRepository.save(user);

        userHistoryRepository.findAll().forEach(System.out::println);
    }

    @Test
    void prePersistTest() {
        User user = new User();
        user.setEmail("martin3@fast.com");
        user.setName("martin");
//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        System.out.println(userRepository.findByEmail("martin2@fast.com"));

    }

    @Test
    void preUpdateTest() {
        User user = new User();
        user.setEmail("martin2@fast.com");
        user.setName("martin");
        userRepository.save(user);

        System.out.println("as - is (기존 유저값) : " + user);

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setName("marttttin2");
        userRepository.save(user2);

        System.out.println("to - be (변경된 값) : " + userRepository.findAll().get(0));
    }

    @Test
    void listenerTest() {
        User user = new User();
        user.setEmail("martin2@fast.com");
        user.setName("martin");

        userRepository.save(user);

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("marttttin");

        userRepository.save(user2);
        System.out.println("to - be (변경된 값) : " + userRepository.findAll().get(0));
        userRepository.deleteById(4L);
    }

    @Test
    void enumTest() {
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setGender(Gender.MALE);

        userRepository.save(user);

        userRepository.findAll().forEach(System.out::println);

        System.out.println(userRepository.findRawRecord().get("gender"));
    }

    @Test
    void insertAndUpdateTest() {
        /*insert*/
        User user = new User();
        user.setName("martin");
        user.setEmail("martin2@fast.com");

        userRepository.save(user);

        /*update*/
        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("marrrrrtin");

        userRepository.save(user2);

    }

    @Test
    void pagingAndSortingTest() {
        System.out.println("findTopByNameOrderByIdDesc : " + userRepository.findTopByNameOrderByIdDesc("martin"));
        System.out.println("findFirstByNameOrderByIdDescEmailAsc : " + userRepository.findFirstByNameOrderByIdDescEmailAsc("martin"));
        System.out.println("findFirstByNameWithSortParams : " + userRepository.findFirstByName("martin", orderByIdDescEmailAsc()));
        System.out.println("findFirstByNameWithSortParams : " + userRepository.findFirstByName("martin", Sort.by(Sort.Order.desc("id"), Sort.Order.asc("email"))));
        System.out.println("findFirstByNameWithSortParams : " + userRepository.findFirstByName("martin", Sort.by(Sort.Order.desc("id"), Sort.Order.asc("email"))));

        //page는 zero-base (0부터 시작)
        System.out.println("findByNameWithPaging : " + userRepository.findByName("martin", PageRequest.of(0, 1, Sort.by(Sort.Order.desc("id")))).getTotalElements());
    }

    //만약 Sort가 너무 길어져서 가독성이 떨어지면 함수를 만들어 사용 가능하다.
    private Sort orderByIdDescEmailAsc() {
        return Sort.by(
                Sort.Order.desc("id"),
                Sort.Order.asc("email"));
    }

    @Test
    void select() {
//        System.out.println(userRepository.findByName("martin"));
//        System.out.println("findByEmail : " + userRepository.findByEmail("martin@fast.com"));
//        System.out.println("getByEmail : " + userRepository.getByEmail("martin@fast.com"));
//        System.out.println("readByEmail : " + userRepository.readByEmail("martin@fast.com"));
//        System.out.println("queryByEmail : " + userRepository.queryByEmail("martin@fast.com"));
//        System.out.println("searchByEmail : " + userRepository.searchByEmail("martin@fast.com"));
//        System.out.println("streamByEmail : " + userRepository.streamByEmail("martin@fast.com"));
//        System.out.println("findUserByEmail : " + userRepository.findUserByEmail("martin@fast.com"));
//
//        System.out.println("findSomethingByEmail : " + userRepository.findSomethingByEmail("martin@fast.com"));

//        System.out.println("findTop2ByName : " + userRepository.findTop2ByName("martin"));
//        System.out.println("findFirst2ByName : " + userRepository.findFirst2ByName("martin"));
//        System.out.println("findLast1ByName : " + userRepository.findLast1ByName("martin"));
        System.out.println("findByEmailAndName : " + userRepository.findByEmailAndName("martin@fast.com", "martin"));
        System.out.println("findByEmailOrName : " + userRepository.findByEmailOrName("martin@fast.com", "dennis"));
        System.out.println("findByCreatedAtAfter : " + userRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByIdAfter : " + userRepository.findByIdAfter(4L));
        System.out.println("findByCreatedAtGreaterThan : " + userRepository.findByCreatedAtGreaterThan(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByCreatedAtGreaterThanEqual : " + userRepository.findByCreatedAtGreaterThanEqual(LocalDateTime.now().minusDays(1L)));
        System.out.println("findByCreatedAtBetween : " + userRepository.findByCreatedAtBetween(LocalDateTime.now().minusDays(1L), LocalDateTime.now().plusDays(1L)));
        System.out.println("findByIdBetween : " + userRepository.findByIdBetween(1L, 3L));
        System.out.println("findByIdGreaterThanEqualAndIdLessThanEqual : " + userRepository.findByIdGreaterThanEqualAndIdLessThanEqual(1L, 3L));
        System.out.println("findByIdIsNotNull : " + userRepository.findByIdIsNotNull());
//        System.out.println("findByIdIsNotEmpty : " + userRepository.findByAddressIsNotEmpty());
        System.out.println("findByNameIn : " + userRepository.findByNameIn(Lists.newArrayList("martin", "dennis")));
        System.out.println("findByNameStartingWith : " + userRepository.findByNameStartingWith("mar"));
        System.out.println("findByNameEndingWith : " + userRepository.findByNameEndingWith("tin"));
        System.out.println("findByNameContains : " + userRepository.findByNameContains("art"));
        System.out.println("findByNameLike : " + userRepository.findByNameLike("%" + "art" + "%"));
    }

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
//        User user = new User();
//        user.setEmail("slow");
//
//        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("email",contains());
//        Example<User> example = Example.of(user, matcher);
//
//
//        userRepository.findAll(example).forEach(System.out::println);
//
//        /*UPDATE*/
//        userRepository.save(new User("david", "david@fast.com"));
//        // 만약 존재하는 엔티티이면 UPDATA를 한다.
//        // 존재하지 않는 엔티티이면 insert한다.
//        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
//        user.setEmail("david-update@fast.com");
//
//        userRepository.save(user);


    }
}