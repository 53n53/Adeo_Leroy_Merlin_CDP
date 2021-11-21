package AdeoLeroyMerlinCDPRecruitment;

import adeo.leroymerlin.cdp.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;


public class EventServiceTest {

    private static final String GRASPOP_METAL_MEETING = "GrasPop Metal Meeting";
    private static final String METALLICA = "Metallica";
    private static final String QUEEN_ANIKA_WALSH = "Queen Anika Walsh";

    private static final String COMMENT = "very good";
    private static final String IMGURL = "img/1000.jpeg";
    private static final Integer NBSTARS = 4;

    @Test
    public void filterEventsTest() {
        // GIVEN
        List<Event> events = new ArrayList<>();

        Set<Member> members00 = new HashSet<>();
        Member member00 = new Member();
        member00.setName("Queen Anika Walsh");
        Member member01 = new Member();
        member01.setName("Queen Katy Stone");
        members00.add(member00);
        members00.add(member01);

        Set<Member> members10 = new HashSet<>();
        Member member10 = new Member();
        member10.setName("Queen Talia Bush");
        members10.add(member10);

        Set<Band> bands00 = new HashSet<>();
        Band band00 = new Band();
        band00.setName(METALLICA);
        band00.setMembers(members00);
        Band band01 = new Band();
        band01.setName("Rolling Stones");
        band01.setMembers(members10);
        bands00.add(band00);
        bands00.add(band01);

        Event event00 = new Event();
        event00.setComment("very good");
        event00.setImgUrl(IMGURL);
        event00.setNbStars(NBSTARS);
        event00.setTitle(GRASPOP_METAL_MEETING);
        event00.setBands(bands00);

        Set<Member> members20 = new HashSet<>();
        Member member20 = new Member();
        member20.setName("Queen Ava Dunlap");
        Member member21 = new Member();
        member21.setName("Queen Haleema Poole");
        members20.add(member20);
        members20.add(member21);

        Set<Band> bands10 = new HashSet<>();
        Band band10 = new Band();
        band10.setName("The Ramones");
        band10.setMembers(members20);
        bands10.add(band10);

        Event event01 = new Event();
        event01.setComment("it was ok");
        event01.setImgUrl("img/1001.jpeg");
        event01.setTitle("Alcatraz Fes");
        event01.setBands(bands10);

        events.add(event00);
        events.add(event01);

        // WHEN
        List<Event> filteredEvents = EventService.filterEvents(events, "Wa");

        // THEN
        assertEquals(1, filteredEvents.size());
        assertEquals(GRASPOP_METAL_MEETING, filteredEvents.get(0).getTitle());
        assertEquals(COMMENT, filteredEvents.get(0).getComment());
        assertEquals(IMGURL, filteredEvents.get(0).getImgUrl());
        assertEquals(NBSTARS, filteredEvents.get(0).getNbStars());

        List<Band> filteredBandsList = new ArrayList<>(filteredEvents.get(0).getBands());
        assertEquals(1, filteredBandsList.size());
        assertEquals(METALLICA, filteredBandsList.get(0).getName());

        List<Member> filteredMembersList = new ArrayList<>(filteredBandsList.get(0).getMembers());
        assertEquals(1, filteredMembersList.size());
        assertEquals(QUEEN_ANIKA_WALSH,filteredMembersList.get(0).getName());
    }

    @Test
    public void isEmptyTest() {
        assertFalse(EventService.isEmpty(null));
        assertTrue(EventService.isEmpty(new HashSet()));

        Set<Member> members = new HashSet<>();
        members.add(new Member());
        assertFalse(EventService.isEmpty(members));
    }

}
