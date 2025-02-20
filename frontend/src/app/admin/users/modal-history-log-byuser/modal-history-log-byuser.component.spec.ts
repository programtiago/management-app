import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalHistoryLogByuserComponent } from './modal-history-log-byuser.component';

describe('ModalHistoryLogByuserComponent', () => {
  let component: ModalHistoryLogByuserComponent;
  let fixture: ComponentFixture<ModalHistoryLogByuserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ModalHistoryLogByuserComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalHistoryLogByuserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
