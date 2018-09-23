package org.mycontrib.spectacle.service;

import java.util.List;

import org.mycontrib.spectacle.entity.Payment;
import org.mycontrib.spectacle.entity.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Override
	public Reservation findReservationById(Long reservationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> findReservationsByCriteria(Long customerId, Long spectacleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation makeReservation(Long customerId, Long sessionId, List<Long> participantIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelReservation(Long reservationId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void AttachPayment(Long reservationId, Payment payment) {
		// TODO Auto-generated method stub

	}

}
