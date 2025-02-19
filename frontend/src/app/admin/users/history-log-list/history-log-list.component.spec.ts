import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryLogListComponent } from './history-log-list.component';

describe('HistoryLogListComponent', () => {
  let component: HistoryLogListComponent;
  let fixture: ComponentFixture<HistoryLogListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HistoryLogListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistoryLogListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
