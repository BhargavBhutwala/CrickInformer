import { Component, OnInit } from '@angular/core';
import { ApiCallService } from '../../services/api-call.service';
import { CommonModule } from '@angular/common';
import { MatchCardComponent } from '../match-card/match-card.component';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule, MatchCardComponent],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements OnInit {

  allMatches: any;

  constructor(private _api: ApiCallService) { }

  ngOnInit(): void {
    this.loadAllMatches();
  }

  private loadAllMatches() {
    this._api.getAllMatches().subscribe({
      next: data => {
        console.log(data);
        this.allMatches = data
      },
      error: error => {
        console.log(error);
      }
    });
  }
}
