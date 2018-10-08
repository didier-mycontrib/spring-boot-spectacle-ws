package org.mycontrib.spectacle.service;

import java.util.Collection;
import java.util.List;

import org.mycontrib.spectacle.dao.ReservationDao;
import org.mycontrib.spectacle.entity.Payment;
import org.mycontrib.spectacle.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {
	
	@Autowired
	private ReservationDao reservationDao;
	
	private static void loadLazyEntity(Object obj){
		if(obj!=null) obj.toString();
	}
	
	private static void loadLazyCollection(Collection<?> c){
		if(c!=null) c.size();
	}

	@Override
	public Reservation findReservationById(Long reservationId) {
		Reservation r =  reservationDao.findOne(reservationId);
		loadLazyEntity(r.getSession());
		loadLazyEntity(r.getSession().getSpectacle());
		loadLazyEntity(r.getPayment());
		loadLazyEntity(r.getCustomer());
		loadLazyCollection(r.getParticipants());
		return r;
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
	public void attachPayment(Long reservationId, Payment payment) {
		Reservation r =  reservationDao.findOne(reservationId);
		r.setPayment(payment);
        //automatic update/merge in persistent state
	}

}
