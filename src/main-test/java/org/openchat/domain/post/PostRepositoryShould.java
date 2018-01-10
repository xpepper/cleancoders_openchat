package org.openchat.domain.post;

import org.junit.Before;
import org.junit.Test;
import org.openchat.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openchat.domain.post.PostBuilder.aPost;
import static org.openchat.domain.user.UserBuilder.aUser;

public class PostRepositoryShould {

    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final LocalDateTime YESTERDAY = NOW.minusDays(1);
    private static final LocalDateTime TWO_HOURS_AGO = NOW.minusHours(2);

    private static final User ALICE = aUser().build();
    private static final Post ALICE_POST_1 = aPost().withUserId(ALICE.userId()).withDateTime(YESTERDAY).build();
    private static final Post ALICE_POST_2 = aPost().withUserId(ALICE.userId()).withDateTime(NOW).build();

    private static final User BOB = aUser().build();
    private static final Post BOB_POST_1 = aPost().withUserId(BOB.userId()).withDateTime(TWO_HOURS_AGO).build();

    private PostRepository postRepository;

    @Before
    public void initialise() {
        postRepository = new PostRepository();
    }

    @Test public void
    return_posts_in_reverse_chronological_order_for_a_given_user() {
        postRepository.add(ALICE_POST_1);
        postRepository.add(BOB_POST_1);
        postRepository.add(ALICE_POST_2);

        List<Post> posts = postRepository.postsInReverseChronologicalOrderFor(ALICE.userId());

        assertThat(posts).containsExactly(ALICE_POST_2, ALICE_POST_1);
    }

    @Test public void
    return_posts_in_reverse_chronological_order_for_the_specified_users() {
        postRepository.add(ALICE_POST_1);
        postRepository.add(BOB_POST_1);
        postRepository.add(ALICE_POST_2);

        List<Post> posts = postRepository.postsInReverseChronologicalOrderFor(asList(ALICE, BOB));

        assertThat(posts).containsExactly(ALICE_POST_2, BOB_POST_1, ALICE_POST_1);
    }

}