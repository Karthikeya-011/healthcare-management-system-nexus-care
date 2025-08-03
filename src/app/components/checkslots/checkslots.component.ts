import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Slots } from 'src/app/models/slots';
import { DoctorService } from 'src/app/services/doctor.service';

@Component({
  selector: 'app-checkslots',
  templateUrl: './checkslots.component.html',
  styleUrls: ['./checkslots.component.css']
})
export class CheckslotsComponent implements OnInit {

  currRole = '';
  loggedUser = '';
  slots : Observable<Slots[]> | undefined;
  
  constructor(private _service : DoctorService) { }



ngOnInit(): void
{
  this.loggedUser = JSON.stringify(sessionStorage.getItem('loggedUser') || '{}');
  this.loggedUser = this.loggedUser.replace(/"/g, '');

  this.currRole = JSON.stringify(sessionStorage.getItem('ROLE') || '{}'); 
  this.currRole = this.currRole.replace(/"/g, '');

  // Fetch slots and filter out expired ones
  this._service.getSlotList().subscribe(data => {
    const today = new Date().toISOString().split('T')[0]; // format: YYYY-MM-DD

    const filtered = data.filter((slot: any) => slot.date >= today);

    // Wrap filtered data in an observable manually
    this.slots = new Observable(observer => {
      observer.next(filtered);
      observer.complete();
    });
  });
}

}
