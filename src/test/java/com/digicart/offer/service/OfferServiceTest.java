package com.digicart.offer.service;

import com.digicart.offer.dto.OfferRequest;
import com.digicart.offer.entity.Offer;
import com.digicart.offer.repository.OfferRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferService offerService;

    @Test
    void findByIdThrowsWhenMissing() {
        UUID missingId = UUID.randomUUID();
        when(offerRepository.findById(missingId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> offerService.findById(missingId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void createPersistsMappedFields() {
        OfferRequest req = new OfferRequest();
        req.setCode("SAVE10");
        req.setType("PERCENT");
        req.setValue(10.0);
        req.setStoreId("store-1");
        when(offerRepository.save(any(Offer.class))).thenAnswer(i -> i.getArgument(0));

        Offer saved = offerService.create(req);
        assertThat(saved.getCode()).isEqualTo("SAVE10");
        assertThat(saved.getType()).isEqualTo("PERCENT");
        verify(offerRepository).save(any(Offer.class));
    }

    @Test
    void incrementUsedCountAddsOne() {
        UUID offerId = UUID.randomUUID();
        Offer offer = new Offer();
        offer.setUsedCount(2);
        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenAnswer(i -> i.getArgument(0));

        Offer updated = offerService.incrementUsedCount(offerId);
        assertThat(updated.getUsedCount()).isEqualTo(3);
    }

    @Test
    void findActiveOffersDelegates() {
        when(offerRepository.findByActiveTrue()).thenReturn(List.of(new Offer()));
        assertThat(offerService.findActiveOffers()).hasSize(1);
    }
}
