import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerStatusComponent } from './player-status.component';

describe('PlayerStatusComponent', () => {
  let component: PlayerStatusComponent;
  let fixture: ComponentFixture<PlayerStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlayerStatusComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlayerStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
