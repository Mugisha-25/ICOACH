package com.example.icoach.service;

import com.example.icoach.model.*;
import com.example.icoach.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitService implements CommandLineRunner {

    private final UserService userService;
    private final ProgramRepository programRepository;
    private final EventRepository eventRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final BoardMemberRepository boardMemberRepository;
    private final ImpactNumberRepository impactNumberRepository;
    private final DonationRepository donationRepository;

    @Override
    public void run(String... args) {
        seedUsers();
        seedPrograms();
        seedEvents();
        seedTeam();
        seedBoard();
        seedImpactNumbers();
        seedSampleDonations();
    }

    private void seedUsers() {
        if (!userService.existsByEmail("admin@bridgehope.org")) {
            userService.register("Super", "Admin", "admin@bridgehope.org", "Admin@2024!", Role.SUPER_ADMIN);
            log.info("Created super admin: admin@bridgehope.org / Admin@2024!");
        }
        if (!userService.existsByEmail("finance@bridgehope.org")) {
            userService.register("Finance", "Manager", "finance@bridgehope.org", "Finance@2024!", Role.FINANCE);
        }
        if (!userService.existsByEmail("comms@bridgehope.org")) {
            userService.register("Communications", "Lead", "comms@bridgehope.org", "Comms@2024!", Role.COMMUNICATIONS);
        }
    }

    private void seedPrograms() {
        if (programRepository.count() > 0) return;

        String[][] programs = {
            {"After-School Excellence Program", "after-school-excellence",
             "Providing academic support, mentorship, and enrichment activities for K-12 students in underserved communities.",
             "Education", "K-12 students", "Mon-Fri, 3:00–6:00 PM", "Main Community Center",
             "fas fa-graduation-cap", "1"},
            {"Early Childhood Learning Center", "early-childhood-learning",
             "Nurturing early development through play-based learning, literacy foundations, and school readiness for children ages 3–5.",
             "Education", "Children ages 3–5", "Mon-Fri, 8:00 AM–4:00 PM", "Main Community Center",
             "fas fa-child", "2"},
            {"Workforce & Economic Empowerment", "workforce-empowerment",
             "Skills training, resume workshops, job placement support, and career coaching for adults seeking employment opportunities.",
             "Workforce", "Adults 18+", "Tuesdays & Thursdays, 6:00–8:00 PM", "Career Lab",
             "fas fa-briefcase", "3"},
            {"Community Dialogue & Meetings", "community-meetings",
             "Regular community gatherings that foster connection, shared learning, and collective problem-solving among residents.",
             "Community", "All community members", "1st Saturday monthly, 10:00 AM", "Community Hall",
             "fas fa-users", "4"},
            {"Driving Support Program", "driving-support",
             "Helping community members obtain driver's licenses through affordable classes, practice sessions, and licensing guidance.",
             "Transportation", "Adults 16+", "Saturdays, 9:00 AM–1:00 PM", "Parking Facility",
             "fas fa-car", "5"},
            {"Summer Enrichment Camp", "summer-enrichment",
             "An immersive eight-week summer program combining academics, arts, sports, and cultural exploration for youth ages 6–17.",
             "Youth", "Youth ages 6–17", "June–August, Mon-Fri", "Various Locations",
             "fas fa-sun", "6"}
        };

        int order = 1;
        for (String[] p : programs) {
            Program prog = Program.builder()
                    .name(p[0])
                    .slug(p[1])
                    .shortDescription(p[2])
                    .fullDescription(p[2] + " Our dedicated staff and volunteers work tirelessly to ensure every participant receives personalized attention and meaningful support. Through evidence-based practices and community-centered approaches, we help families build stronger futures together.")
                    .category(p[3])
                    .targetAudience(p[4])
                    .schedule(p[5])
                    .location(p[6])
                    .iconClass(p[7])
                    .active(true)
                    .displayOrder(Integer.parseInt(p[8]))
                    .build();
            programRepository.save(prog);
        }
        log.info("Seeded {} programs", programs.length);
    }

    private void seedEvents() {
        if (eventRepository.count() > 0) return;

        LocalDateTime now = LocalDateTime.now();
        Event e1 = Event.builder()
                .title("Annual Community Gala & Fundraiser")
                .summary("Join us for our signature annual celebration honoring community champions and raising funds for our programs.")
                .description("Our Annual Community Gala brings together families, partners, and supporters for an evening of inspiration, recognition, and celebration. Enjoy dinner, live entertainment, and hear stories of transformation from program participants.")
                .location("Des Moines Convention Center, 700 3rd St, Des Moines, IA")
                .startDate(now.plusDays(30).withHour(18).withMinute(0))
                .endDate(now.plusDays(30).withHour(22).withMinute(0))
                .category("Fundraiser")
                .registrationRequired(true)
                .maxAttendees(300)
                .published(true)
                .featured(true)
                .build();

        Event e2 = Event.builder()
                .title("Family Literacy & Resource Fair")
                .summary("Free event connecting families with educational resources, community services, and literacy materials.")
                .description("Bring the whole family! This free resource fair features hands-on literacy activities, access to community service providers, free books, health screenings, and more. All materials available in multiple languages.")
                .location("BridgeHope Community Center, 123 Community Drive")
                .startDate(now.plusDays(14).withHour(10).withMinute(0))
                .endDate(now.plusDays(14).withHour(15).withMinute(0))
                .category("Community")
                .registrationRequired(false)
                .published(true)
                .featured(true)
                .build();

        Event e3 = Event.builder()
                .title("Volunteer Orientation & Training")
                .summary("New volunteer orientation covering our mission, programs, and how you can make a difference.")
                .description("Are you ready to make a difference? Join us for this comprehensive orientation where you'll learn about our programs, meet staff, and discover how your unique skills can support our community. Light refreshments provided.")
                .location("BridgeHope Community Center, Conference Room A")
                .startDate(now.plusDays(7).withHour(9).withMinute(0))
                .endDate(now.plusDays(7).withHour(12).withMinute(0))
                .category("Volunteer")
                .registrationRequired(true)
                .maxAttendees(25)
                .published(true)
                .build();

        eventRepository.save(e1);
        eventRepository.save(e2);
        eventRepository.save(e3);
        log.info("Seeded 3 events");
    }

    private void seedTeam() {
        if (teamMemberRepository.count() > 0) return;

        String[][] staff = {
            {"Maria", "Okonkwo", "Executive Director",
             "Maria brings over 15 years of nonprofit leadership experience and a deep personal connection to the communities we serve. Under her leadership, BridgeHope has expanded programs serving thousands of families annually.",
             "1"},
            {"James", "Ndukwe", "Director of Programs",
             "James oversees all educational and community programs, ensuring each initiative is grounded in evidence-based practices and responsive to community needs.",
             "2"},
            {"Fatima", "Al-Hassan", "Workforce Development Coordinator",
             "Fatima designs and delivers workforce training programs, maintaining strong relationships with local employers and workforce development partners.",
             "3"},
            {"David", "Hernandez", "Youth Program Manager",
             "David leads our after-school and summer enrichment programs, creating safe, engaging environments where young people can thrive academically and personally.",
             "4"},
            {"Anika", "Patel", "Community Outreach Coordinator",
             "Anika connects families to resources and builds bridges between our organization and the broader community through events, partnerships, and advocacy.",
             "5"},
            {"Samuel", "Kimura", "Operations & Finance Manager",
             "Samuel ensures organizational effectiveness through sound financial management, compliance, and operational systems that support our mission.",
             "6"}
        };

        int order = 1;
        for (String[] s : staff) {
            TeamMember member = TeamMember.builder()
                    .firstName(s[0])
                    .lastName(s[1])
                    .title(s[2])
                    .bio(s[3])
                    .imageUrl("https://ui-avatars.com/api/?name=" + s[0] + "+" + s[1] + "&size=300&background=1B3A6B&color=fff")
                    .active(true)
                    .staff(true)
                    .displayOrder(Integer.parseInt(s[4]))
                    .build();
            teamMemberRepository.save(member);
        }
        log.info("Seeded {} team members", staff.length);
    }

    private void seedBoard() {
        if (boardMemberRepository.count() > 0) return;

        String[][] board = {
            {"Rev. Thomas", "Achebe", "Board Chair", "Faith & Community Leader",
             "Rev. Achebe has served our community for over 20 years and brings strong spiritual and civic leadership to our board.", "2022–Present", "1"},
            {"Dr. Priya", "Sharma", "Vice Chair", "Healthcare Executive",
             "Dr. Sharma brings expertise in community health and a passion for addressing systemic inequities that affect vulnerable populations.", "2021–Present", "2"},
            {"Carlos", "Martinez", "Treasurer", "CPA, Financial Advisor",
             "Carlos provides financial oversight and strategic planning expertise, ensuring organizational sustainability and accountability.", "2020–Present", "3"},
            {"Dr. Linda", "Washington", "Secretary", "University Professor",
             "Dr. Washington's expertise in education policy and program evaluation strengthens our evidence-based approach to program development.", "2023–Present", "4"}
        };

        for (String[] b : board) {
            BoardMember member = BoardMember.builder()
                    .firstName(b[0])
                    .lastName(b[1])
                    .title(b[2])
                    .profession(b[3])
                    .bio(b[4])
                    .imageUrl("https://ui-avatars.com/api/?name=" + b[0].replace("Rev. ", "").replace("Dr. ", "") + "+" + b[1] + "&size=300&background=F4A41B&color=fff")
                    .term(b[5])
                    .displayOrder(Integer.parseInt(b[6]))
                    .active(true)
                    .build();
            boardMemberRepository.save(member);
        }
        log.info("Seeded {} board members", board.length);
    }

    private void seedImpactNumbers() {
        if (impactNumberRepository.count() > 0) return;

        ImpactNumber[] numbers = {
            ImpactNumber.builder().label("Families Served").value("2,400+").description("families received direct services last year").iconClass("fas fa-home").displayOrder(1).active(true).build(),
            ImpactNumber.builder().label("Youth Enrolled").value("850+").description("youth participated in educational programs").iconClass("fas fa-graduation-cap").displayOrder(2).active(true).build(),
            ImpactNumber.builder().label("Volunteer Hours").value("12,000+").description("hours contributed by dedicated volunteers").iconClass("fas fa-hands-helping").displayOrder(3).active(true).build(),
            ImpactNumber.builder().label("Years of Service").value("10+").description("years empowering our community").iconClass("fas fa-star").displayOrder(4).active(true).build(),
            ImpactNumber.builder().label("Job Placements").value("320+").description("adults placed in employment through our programs").iconClass("fas fa-briefcase").displayOrder(5).active(true).build(),
            ImpactNumber.builder().label("Partner Organizations").value("45+").description("community partners and sponsors supporting our work").iconClass("fas fa-handshake").displayOrder(6).active(true).build()
        };

        for (ImpactNumber n : numbers) {
            impactNumberRepository.save(n);
        }
        log.info("Seeded {} impact numbers", numbers.length);
    }

    private void seedSampleDonations() {
        if (donationRepository.count() > 0) return;

        Donation d1 = Donation.builder()
                .donorFirstName("Sarah").donorLastName("Johnson").donorEmail("sarah@example.com")
                .amount(new BigDecimal("100.00")).donationType(Donation.DonationType.ONE_TIME)
                .status(Donation.DonationStatus.COMPLETED).purpose("General Support")
                .paymentMethod("Credit Card").build();

        Donation d2 = Donation.builder()
                .donorFirstName("Michael").donorLastName("Chen").donorEmail("mchen@example.com")
                .amount(new BigDecimal("50.00")).donationType(Donation.DonationType.MONTHLY)
                .status(Donation.DonationStatus.COMPLETED).purpose("Youth Programs")
                .paymentMethod("Bank Transfer").build();

        Donation d3 = Donation.builder()
                .donorFirstName("Anonymous").donorLastName("Donor").donorEmail("anon@example.com")
                .amount(new BigDecimal("200.00")).donationType(Donation.DonationType.ONE_TIME)
                .status(Donation.DonationStatus.COMPLETED).purpose("Workforce Development")
                .anonymous(true).paymentMethod("Credit Card").build();

        donationRepository.save(d1);
        donationRepository.save(d2);
        donationRepository.save(d3);
        log.info("Seeded sample donations");
    }
}
