// import {Component, OnInit} from '@angular/core';
// import {BorrowService} from "../services/borrow.service";
// import {UserService} from "../services/user.service";
//
// @Component({
//   selector: 'app-user-borrowings',
//   templateUrl: './user-borrowings.component.html',
//   styleUrl: './user-borrowings.component.css'
// })
// export class UserBorrowingsComponent implements OnInit{
//   borrowings: any[] = [];
//   userId!: number;
//
//   constructor(private borrowingService: BorrowService, private userService: UserService) {
//   }
//   ngOnInit(): void {
//     this.userService.userId$.subscribe(userId => {
//       if (userId) {
//         this.loadUserBorrowings();
//       } else {
//         console.log('error when get userId');
//       }
//     });
//   }
//
//   loadUserBorrowings(): void {
//     this.borrowingService.getUserBorrowings(this.userId).subscribe(borrowings => {
//       this.borrowings = borrowings;
//     },
//       error => {
//       console.log('Error loading user borrowings: ', error);
//       });
//   }
//
//   getBookCoverImage(bookId: any) {
//
//   }
// }
