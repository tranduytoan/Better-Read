import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})
export class PaginationComponent {
  @Input() currentPage: number = 0;
  @Input() totalPages: number = 0;
  @Input() maxVisiblePages: number = 5;
  @Output() pageChange: EventEmitter<number> = new EventEmitter<number>();

  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.pageChange.emit(page);
    }
  }

  getVisiblePages() {
    let startPage = Math.max(this.currentPage - Math.floor(this.maxVisiblePages / 2), 0);
    let endPage = Math.min(startPage + this.maxVisiblePages, this.totalPages);

    if (endPage - startPage < this.maxVisiblePages) {
      startPage = Math.max(endPage - this.maxVisiblePages, 0);
    }

    return Array.from({ length: endPage - startPage }, (_, i) => startPage + i);
  }
}
