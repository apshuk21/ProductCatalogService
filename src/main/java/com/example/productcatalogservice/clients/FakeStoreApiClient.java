package com.example.productcatalogservice.clients;

import com.example.productcatalogservice.dtos.FakeStoreProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakeStoreApiClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request,
                                                   Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    public FakeStoreProductDTO getFakeStoreProductById(Long productId) {
        ResponseEntity<FakeStoreProductDTO> fakeProductResponseEntity =
                requestForEntity("https://fakestoreapi.com/products/{productId}", HttpMethod.GET, null, FakeStoreProductDTO.class, productId);

        return validateFakeStoreProductResponse(fakeProductResponseEntity);
    }

    public List<FakeStoreProductDTO> getAllFakeStoreProducts() {
        List<FakeStoreProductDTO> fakeStoreProducts = new ArrayList<>();

        ResponseEntity<FakeStoreProductDTO[]> fakeStoreProductsResponseEntity = requestForEntity("https://fakestoreapi.com/products", HttpMethod.GET, null, FakeStoreProductDTO[].class);

        FakeStoreProductDTO[] fakeStoreProductDTOs = validateFakeStoreProductsResponse(fakeStoreProductsResponseEntity);

        for (FakeStoreProductDTO fakeStoreProductDTO : fakeStoreProductDTOs) {
            if (fakeStoreProductDTO != null) {
                fakeStoreProducts.add(fakeStoreProductDTO);
            }
        }

        return fakeStoreProducts;
    }

    public FakeStoreProductDTO updateFakeStoreProductById(Long productId, FakeStoreProductDTO fakeStoreProductDTO) {
        ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTOResponseEntity = requestForEntity("https://fakestoreapi.com/products/{productId}", HttpMethod.PUT, fakeStoreProductDTO, FakeStoreProductDTO.class, productId);
        return validateFakeStoreProductResponse(fakeStoreProductDTOResponseEntity);
    }

    private FakeStoreProductDTO validateFakeStoreProductResponse(ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTOResponseEntity) {
        if (fakeStoreProductDTOResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProductDTOResponseEntity.getBody() != null) {
            return fakeStoreProductDTOResponseEntity.getBody();
        }

        return null;
    }

    private FakeStoreProductDTO[] validateFakeStoreProductsResponse(ResponseEntity<FakeStoreProductDTO[]> fakeStoreProductDTOResponseEntity) {
        if (fakeStoreProductDTOResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProductDTOResponseEntity.getBody() != null) {
            return fakeStoreProductDTOResponseEntity.getBody();
        }

        return null;
    }
}
