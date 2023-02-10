package br.com.totvs.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import spark.Response;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
public class ResponseResourceTest {

    private Response response;
    private ResponseResource<String> responseResource;

    @BeforeEach
    void setUp() {
        response = Mockito.mock(Response.class);
        doReturn(200).when(response).status();
    }


    @Test
    @DisplayName("Teste de retornar um ResponseResource com sucesso")
    void testOfResponseResource() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String resource = "resource";
        TypeStatus typeStatus = TypeStatus.OK;

        Class<ResponseResource> resourceTest = ResponseResource.class;
        Constructor<ResponseResource> constructor = resourceTest.getDeclaredConstructor(Object.class, String.class, TypeStatus.class);
        constructor.setAccessible(true);
        responseResource = (ResponseResource<String>) constructor.newInstance("resource", null, TypeStatus.OK);

        Assertions.assertEquals(responseResource, ResponseResource.of(response, resource, typeStatus));

    }

    @Test
    @DisplayName("Teste de retornar um ResponseResource com ErrorMessage com sucesso")
    void testOfResponseResourceWithErrorMessage() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        TypeStatus typeStatus = TypeStatus.BAD_REQUEST;
        String errorMessage = "errorMessage";

        Class<ResponseResource> resourceTest = ResponseResource.class;
        Constructor<ResponseResource> constructor = resourceTest.getDeclaredConstructor(Object.class, String.class, TypeStatus.class);
        constructor.setAccessible(true);
        responseResource = (ResponseResource<String>) constructor.newInstance(null, "errorMessage", TypeStatus.BAD_REQUEST);

        Assertions.assertEquals(responseResource, ResponseResource.ofError(response, errorMessage, typeStatus));

    }

}
