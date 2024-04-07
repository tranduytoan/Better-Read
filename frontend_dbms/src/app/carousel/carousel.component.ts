import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrl: './carousel.component.css'
})
export class CarouselComponent implements OnInit {
  carouselItems = [
    { image: '/assets/images/carousel1.jpg', title: 'Slide 1', description: 'Description for slide 1' },
    { image: '/assets/images/carousel2.jpg', title: 'Slide 2', description: 'Description for slide 2' },
    { image: '/assets/images/carousel3.jpg', title: 'Slide 3', description: 'Description for slide 3' }
  ];
  activeIndex = 0;

  constructor() { }

  ngOnInit(): void {
  }

  previousSlide() {
    this.activeIndex = (this.activeIndex - 1 + this.carouselItems.length) % this.carouselItems.length;
  }

  nextSlide() {
    this.activeIndex = (this.activeIndex + 1) % this.carouselItems.length;
  }

  goToSlide(index: number) {
    this.activeIndex = index;
  }

  handleMouseMove(event: MouseEvent) {
    // // @ts-ignore
    // const containerWidth = event.currentTarget.clientWidth;
    // const mouseX = event.offsetX;
    //
    // if (mouseX > containerWidth / 2) {
    //   this.nextSlide();
    // }
  }
}
