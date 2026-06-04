package com.example.icoach.service;

import com.example.icoach.model.ContactMessage;
import com.example.icoach.model.NewsletterSubscriber;
import com.example.icoach.repository.ContactMessageRepository;
import com.example.icoach.repository.NewsletterSubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {

    private final ContactMessageRepository contactMessageRepository;
    private final NewsletterSubscriberRepository subscriberRepository;

    public ContactMessage save(ContactMessage message) {
        return contactMessageRepository.save(message);
    }

    public List<ContactMessage> findAll() {
        return contactMessageRepository.findAll();
    }

    public Optional<ContactMessage> findById(Long id) {
        return contactMessageRepository.findById(id);
    }

    public List<ContactMessage> findByStatus(ContactMessage.MessageStatus status) {
        return contactMessageRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public long countNew() {
        return contactMessageRepository.countByStatus(ContactMessage.MessageStatus.NEW);
    }

    public void markAsRead(Long id) {
        contactMessageRepository.findById(id).ifPresent(m -> {
            if (m.getStatus() == ContactMessage.MessageStatus.NEW) {
                m.setStatus(ContactMessage.MessageStatus.READ);
                contactMessageRepository.save(m);
            }
        });
    }

    public void markAsReplied(Long id) {
        contactMessageRepository.findById(id).ifPresent(m -> {
            m.setStatus(ContactMessage.MessageStatus.REPLIED);
            contactMessageRepository.save(m);
        });
    }

    public void delete(Long id) {
        contactMessageRepository.deleteById(id);
    }

    public String subscribeNewsletter(String email, String firstName) {
        if (subscriberRepository.existsByEmail(email)) {
            return "already_subscribed";
        }
        NewsletterSubscriber subscriber = NewsletterSubscriber.builder()
                .email(email)
                .firstName(firstName)
                .active(true)
                .build();
        subscriberRepository.save(subscriber);
        return "success";
    }

    public List<NewsletterSubscriber> findAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public long countSubscribers() {
        return subscriberRepository.count();
    }
}
