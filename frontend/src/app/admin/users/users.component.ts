import { Component, OnInit, ViewChild } from '@angular/core';
import { AdminService } from '../services/admin.service';
import { catchError, Observable, of, tap } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../shared/components/error-dialog/error-dialog.component';
import { UserPage } from '../../model/user/user-page';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { User } from '../../model/user/user';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent implements OnInit{

  users$: Observable<UserPage> | null = null;
  users: User[] = []

  dataSource = new MatTableDataSource<User>(this.users)

  @ViewChild(MatPaginator) paginator!: MatPaginator

  pageIndex = 0;
  pageSize = 10;
  
  constructor(private adminService: AdminService, private dialog: MatDialog){
    this.refresh();
  }

  ngOnInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  refresh(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10}){
    this.users$ = this.adminService.listAllUsers(pageEvent.pageIndex, pageEvent.pageSize)
      .pipe(
        tap(() => {
          this.pageIndex = pageEvent.pageIndex;
          this.pageSize = pageEvent.pageSize;
        }),
        catchError(error => {
          this.onError('Error loading users');
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

  onFilteredResults(event: { users: User[]; totalElements: number}){
    this.users = event.users;
    this.dataSource.data = this.users;
    this.paginator.length = event.totalElements;
    this.paginator.firstPage();
  }
}
