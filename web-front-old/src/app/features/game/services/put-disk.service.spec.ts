import {TestBed} from '@angular/core/testing';

import {PutDiskService} from './put-disk.service';
import {provideHttpClient} from "@angular/common/http";

describe('PutDiskService', () => {
  let service: PutDiskService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient()],
    });
    service = TestBed.inject(PutDiskService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
