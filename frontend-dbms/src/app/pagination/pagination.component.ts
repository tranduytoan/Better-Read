import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.scss'
})
export class PaginationComponent {
  @Input() searchBooks: Function = () => {};
  @Input() searchQuery: string = '';
  @Input() currentPage: number = 1;
  @Input() totalPages: number = 1;
  @Input() maxVisiblePages: number = 5;
  @Output() pageChange: EventEmitter<number> = new EventEmitter<number>();


  goToPage(page: number, event: any) {
    event.preventDefault();
    this.pageChange.emit(
      page
    );
  }

  goToNextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.searchBooks(this.searchQuery, this.currentPage);
    }
  }

  goToPreviousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.searchBooks(this.searchQuery, this.currentPage);
    }
  }

  getVisiblePages() {
    let startPage = Math.max(this.currentPage - Math.floor(this.maxVisiblePages / 2), 0);
    let endPage = Math.min(startPage + this.maxVisiblePages, this.totalPages);

    if (endPage - startPage < this.maxVisiblePages) {
      startPage = Math.max(endPage - this.maxVisiblePages, 0);
    }

    return Array.from({length: endPage - startPage}, (_, i) => startPage + i);
  }
}
