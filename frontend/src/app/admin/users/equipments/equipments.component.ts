import { Component } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';
import { Equipment } from '../../../model/equiment';
import { AdminService } from '../../services/admin.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';

@Component({
  selector: 'app-equipments',
  templateUrl: './equipments.component.html',
  styleUrl: './equipments.component.scss'
})
export class EquipmentsComponent {

  equipments$: Observable<Equipment[]>;

  constructor(private adminService: AdminService, private dialog: MatDialog, 
    private snackbar: MatSnackBar){
      this.equipments$ = this.adminService.getEquipments().pipe(
        catchError(error => {
          this.onError('Error loading equipments.')
          return of([])
        })
      )
    }

    onError(errorMsg: string){
      this.dialog.open(ErrorDialogComponent, {
        data: 
          errorMsg
      })
    }
}
