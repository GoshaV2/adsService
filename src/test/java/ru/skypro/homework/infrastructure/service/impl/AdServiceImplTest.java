package ru.skypro.homework.infrastructure.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.core.mapper.AdMapper;
import ru.skypro.homework.core.model.Ad;
import ru.skypro.homework.core.model.Role;
import ru.skypro.homework.core.model.User;
import ru.skypro.homework.core.repository.AdRepository;
import ru.skypro.homework.core.repository.CommentRepository;
import ru.skypro.homework.core.repository.FileRepository;
import ru.skypro.homework.infrastructure.dto.request.AdRequest;
import ru.skypro.homework.infrastructure.dto.response.AdResponse;
import ru.skypro.homework.infrastructure.exception.NotFoundElementException;
import ru.skypro.homework.infrastructure.facade.AuthenticationFacade;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {
    @Mock
    private AdRepository adRepository;
    @Mock
    private AuthenticationFacade authenticationFacade;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private CommentRepository commentRepository;
    @Spy
    private AdMapper adMapper = Mappers.getMapper(AdMapper.class);
    @InjectMocks
    private AdServiceImpl adService;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(adService, "adImageUrl", "/ads/image/%d");
    }

    private void mockAuthentication(User user) {
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
    }

    private void mockAdRepositorySaveWithSettingId(long id) {
        when(adRepository.save(any())).thenAnswer(i -> {
            Ad argument = (Ad) i.getArguments()[0];
            argument.setId(id);
            return argument;
        });
    }

    private void mockAdRepositorySaveWithoutSettingId() {
        when(adRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
    }

    private void mockAdRepositoryFindById(long id, Ad ad) {
        when(adRepository.findById(id)).thenReturn(Optional.of(ad));
    }

    private void mockAdRepositoryFindByIdAndAuthorId(long id, long authorId, Ad ad) {
        when(adRepository.findByIdAndAuthorId(id, authorId)).thenReturn(Optional.of(ad));
    }

    private void mockAdRepositoryFindByIdAndAuthorId_returnEmptyOptional(long id, long authorId) {
        when(adRepository.findByIdAndAuthorId(id, authorId)).thenReturn(Optional.empty());
    }

    @Test
    void addAd_whenSuccess_thenReturnResponse() throws IOException {
        User user = getDefaultUserWithoutImage();
        AdRequest adRequest = getAdRequest();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        mockAuthentication(user);
        mockAdRepositorySaveWithSettingId(1);

        AdResponse adResponse = adService.addAd(adRequest, multipartFile);

        verify(fileRepository).addFile(any(), any());
        verify(adRepository).save(any());
        assertEquals(adResponse.getId(), 1L);
        assertEquals(adResponse.getPrice(), adRequest.getPrice());
        assertEquals(adResponse.getUserId(), user.getId());
        assertEquals(adResponse.getImageUrl(), "/ads/image/1");
    }

    @Test
    void removeAd_whenEditorISAuthor_thenSuccessRemove() {
        User editor = getDefaultUser();
        Ad ad = getAd(editor);
        mockAuthentication(editor);
        mockAdRepositoryFindByIdAndAuthorId(1, editor.getId(), ad);

        adService.removeAd(1);

        verify(adRepository).delete(ad);
        verify(commentRepository).deleteAllByAd(ad);
    }

    @Test
    void removeAd_whenEditorIsAdmin_thenSuccessRemove() {
        User editor = getAdminUser();
        User author = getDefaultUserWithoutImage();
        Ad ad = getAd(author);
        mockAuthentication(editor);
        mockAdRepositoryFindById(1, ad);

        adService.removeAd(1);

        verify(adRepository).delete(ad);
        verify(commentRepository).deleteAllByAd(ad);
    }


    @Test
    void removeAd_whenEditorIsNotAuthor_thenThrowNotFoundException() {
        User editor = getDefaultUser();
        mockAuthentication(editor);
        mockAdRepositoryFindByIdAndAuthorId_returnEmptyOptional(1, editor.getId());

        assertThrows(NotFoundElementException.class, () -> adService.removeAd(1));
        verify(adRepository, never()).delete(any());
        verify(commentRepository, never()).deleteAllByAd(any());
    }

    @Test
    void updateAds_whenEditorIsAuthor_thenSuccessUpdate() {
        User author = getDefaultUserWithoutImage();
        Ad ad = getAd(author);
        AdRequest adRequest = getAdRequest();
        mockAuthentication(author);
        mockAdRepositorySaveWithoutSettingId();
        mockAdRepositoryFindByIdAndAuthorId(1, author.getId(), ad);

        AdResponse adResponse = adService.updateAds(1L, adRequest);

        verify(adRepository).save(any());
        assertEquals(adResponse.getId(), 1L);
        assertEquals(adResponse.getPrice(), adRequest.getPrice());
        assertEquals(adResponse.getUserId(), ad.getAuthor().getId());
        assertEquals(adResponse.getImageUrl(), "/ads/image/1");
    }

    @Test
    void updateAds_whenAdminIsAuthor_thenSuccessUpdate() {
        User author = getDefaultUserWithoutImage();
        User editor = getAdminUser();
        Ad ad = getAd(author);
        AdRequest adRequest = getAdRequest();
        mockAuthentication(editor);
        mockAdRepositorySaveWithoutSettingId();
        mockAdRepositoryFindById(1, ad);

        AdResponse adResponse = adService.updateAds(1L, adRequest);

        verify(adRepository).save(any());
        assertEquals(adResponse.getId(), 1L);
        assertEquals(adResponse.getPrice(), adRequest.getPrice());
        assertEquals(adResponse.getUserId(), ad.getAuthor().getId());
        assertEquals(adResponse.getImageUrl(), "/ads/image/1");
    }

    @Test
    void updateAds_whenEditorIsNotAuthor_thenThrowNotFoundException() {
        User editor = getDefaultUser();
        AdRequest adRequest = getAdRequest();
        mockAuthentication(editor);
        mockAdRepositoryFindByIdAndAuthorId_returnEmptyOptional(1, editor.getId());

        assertThrows(NotFoundElementException.class, () -> adService.updateAds(1L, adRequest));
        verify(adRepository, never()).save(any());
    }

    private User getDefaultUserWithoutImage() {
        return User.builder()
                .id(1L)
                .email("test@mail.ru")
                .firstName("name")
                .lastName("surname")
                .password("Password")
                .phone("phone")
                .role(Role.USER)
                .build();
    }

    private User getDefaultUser() {
        return User.builder()
                .id(3L)
                .email("test@mail.ru")
                .firstName("name")
                .lastName("surname")
                .password("Password")
                .phone("phone")
                .role(Role.USER)
                .userImageUrl("image")
                .build();
    }

    private User getAdminUser() {
        return User.builder()
                .id(2L)
                .email("test@mail.ru")
                .firstName("name")
                .lastName("surname")
                .password("Password")
                .phone("phone")
                .role(Role.ADMIN)
                .userImageUrl("image")
                .build();
    }

    private AdRequest getAdRequest() {
        return AdRequest.builder()
                .description("description")
                .title("title")
                .price(1000)
                .build();
    }

    private Ad getAd(User author) {
        return Ad.builder()
                .id(1L)
                .author(author)
                .description("description1")
                .title("title1")
                .price(1001L)
                .build();
    }
}