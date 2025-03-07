import { Component, ViewChild } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { Equipment } from '../../../model/equipment/equiment';
import { AdminService } from '../../services/admin.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { EquipmentPage } from '../../../model/equipment/equipment-page';

@Component({
  selector: 'app-equipments',
  templateUrl: './equipments.component.html',
  styleUrl: './equipments.component.scss'
})
export class EquipmentsComponent {

  equipments$: Observable<EquipmentPage> | null = null;

  @ViewChild(MatPaginator, {static: true}) paginator!: MatPaginator

  pageIndex = 0;
  pageSize = 10;

  constructor(private adminService: AdminService, private dialog: MatDialog, 
    private snackbar: MatSnackBar){
      this.refresh();
    }

    refresh(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10}){
      this.equipments$ = this.adminService.getEquipments(pageEvent.pageIndex, pageEvent.pageSize)
        .pipe(
          tap(() => {
            this.pageIndex = pageEvent.pageIndex;
            this.pageSize = pageEvent.pageSize;
          }),
          catchError(error => {
            this.onError('Error loading equipments');
            return of({ content: [], totalElements: 0, totalPages: 0})
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
