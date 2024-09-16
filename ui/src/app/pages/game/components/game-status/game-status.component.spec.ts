import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameStatusComponent } from './game-status.component';

describe('GameStatusComponent', () => {
  let component: GameStatusComponent;
  let fixture: ComponentFixture<GameStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GameStatusComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
