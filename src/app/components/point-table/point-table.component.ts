import { Component, OnInit } from '@angular/core';
import { ApiCallService } from '../../services/api-call.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-point-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './point-table.component.html',
  styleUrl: './point-table.component.css'
})
export class PointTableComponent implements OnInit {

  pointsTable: any;
  tableHeading: any;

  constructor(private _api:ApiCallService) { }

  ngOnInit(): void {
    this._api.getPointsTable().subscribe({
      next: data=>{
        this.pointsTable = data;
        //console.log(this.pointsTable);
        this.tableHeading = [...this.pointsTable[0]];
        console.log(this.tableHeading)
      },
      error: error=>{
        console.log(error);
      }
    })
  }

}
