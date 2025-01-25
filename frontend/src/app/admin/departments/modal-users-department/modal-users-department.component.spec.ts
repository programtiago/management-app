import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalUsersDepartmentComponent } from './modal-users-department.component';

describe('ModalUsersDepartmentComponent', () => {
  let component: ModalUsersDepartmentComponent;
  let fixture: ComponentFixture<ModalUsersDepartmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalUsersDepartmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalUsersDepartmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
