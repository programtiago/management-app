import { Component, Inject } from '@angular/core';
import { HistoryLog } from '../../../model/history-log/history-log';
import { AdminService } from '../../services/admin.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-history-log-byuser',
  templateUrl: './modal-history-log-byuser.component.html',
  styleUrl: './modal-history-log-byuser.component.scss'
})
export class ModalHistoryLogByuserComponent {

  historyLogs: HistoryLog[] = []

  constructor(public dialogRef: MatDialogRef<ModalHistoryLogByuserComponent>,
      @Inject(MAT_DIALOG_DATA) public data: { userId: number, userEquipments: any}, private adminService: AdminService){}

}
