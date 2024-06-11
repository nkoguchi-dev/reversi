import { TestBed } from '@angular/core/testing';

import { GameStartService } from './game-start.service';

describe('GameStartService', () => {
  let service: GameStartService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GameStartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
