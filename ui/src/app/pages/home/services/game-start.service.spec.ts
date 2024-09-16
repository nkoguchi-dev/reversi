import {TestBed} from '@angular/core/testing';

import {GameStartService} from './game-start.service';
import {provideHttpClient} from "@angular/common/http";

describe('GameStartService', () => {
  let service: GameStartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient()],
    });
    service = TestBed.inject(GameStartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
