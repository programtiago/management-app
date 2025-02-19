import { Component } from '@angular/core';
import { HistoryLog } from '../../../model/history-log/history-log';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-history-log-list',
  templateUrl: './history-log-list.component.html',
  styleUrl: './history-log-list.component.scss'
})
export class HistoryLogListComponent {

  historyLogs: HistoryLog[] = []
  displayedColumns : string[] = ["action", "timestamp", "user"];

  constructor(private adminService: AdminService){
    this.adminService.getAllHistoryLogs().subscribe((res) => {
      this.historyLogs = res;
      console.log(this.historyLogs)
    })
  }

}
