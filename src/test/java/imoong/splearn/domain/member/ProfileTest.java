package imoong.splearn.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ProfileTest {
    
    @Test
    void profile() {
        new Profile("imoong");
        new Profile("kw3082");
        new Profile("1234");
    }

    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("AA")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("sdfasdfasdflasdfsadflkjsadf")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    void url() {
        var profile = new Profile("kw3082");

        assertThat(profile.url()).isEqualTo("@kw3082");
    }

}