import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ScrollService {

  constructor() { }
  scrollToTop() {
    window.scroll(50, 50);
  }
}
