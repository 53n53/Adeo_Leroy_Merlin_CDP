package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.delete(id);
    }

    public Event upsertEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here

        return filterEvents(events, query);
    }

    /**
     * Filter the events, returns events that contains bands which contains members that have name containing the query.
     * Not case-sensitive.
     *
     * @param pEvents the list of events to be filtered
     * @param pQuery the query (member's name)
     * @return the {@link List<Event>} of filtered events
     */
    public static List<Event> filterEvents(List<Event> pEvents, String pQuery) {
        List<Event> lEventsCopy = new ArrayList<>(pEvents);

        return lEventsCopy.stream()
                .peek(event -> event.setBands(filterBandsByMembersName(event, pQuery)))
                .filter(event -> !isEmpty(event.getBands()))
                .collect(Collectors.toList());
    }

    /**
     * Filter the bands of the given event, returns bands and their member that have a name containing the query.
     * Not case-sensitive.
     *
     * @param pEvent the {@link Event} which contains the {@link Band} to filter
     * @param pQuery the query (member's name)
     * @return the {@link Set <Band>} of filtered bands
     */
    private static Set<Band> filterBandsByMembersName(Event pEvent, String pQuery) {
        return pEvent.getBands().stream()
                .peek(band -> band.setMembers(filterMembersByName(band, pQuery)))
                .filter(band -> !isEmpty(band.getMembers()))
                .collect(Collectors.toSet());
    }

    /**
     * Filter the members of the given band, returns member that have a name containing the query.
     * Not case-sensitive.
     *
     * @param pBand the {@link Band} which contains the {@link Member} to filter
     * @param pQuery the query (member's name)
     * @return the {@link Set<Member>} of filtered members
     */
    private static Set<Member> filterMembersByName(Band pBand, String pQuery) {
        String lQuery = pQuery.toLowerCase();
        return pBand.getMembers().stream()
                .filter(member -> member.getName().toLowerCase().contains(lQuery))
                .collect(Collectors.toSet());
    }

    /**
     * Null-safe check if a collection is empty.
     *
     * @param pCollection, the {@link Collection} to check
     * @return true if the collection is not null and empty, false if null or not empty.
     */
    public static Boolean isEmpty(Collection<?> pCollection) {
        return pCollection != null && pCollection.isEmpty();
    }
}
