package org.mycontrib.spectacle.service;

import java.util.List;

import org.mycontrib.spectacle.entity.Payment;
import org.mycontrib.spectacle.entity.Reservation;




public interface ReservationService {
	public Reservation findReservationById(Long reservationId);
	public List<Reservation> findReservationsByCriteria(Long customerId,Long spectacleId); //each criteria may be null
	public Reservation makeReservation(Long customerId, Long sessionId, List<Long> participantIds);
	public void cancelReservation(Long reservationId);
	public void AttachPayment(Long reservationId, Payment payment);
}
