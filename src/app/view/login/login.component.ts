import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MessageComponent} from "../../util/dialog/message/message.component";
import {AuthenticateService} from "../../service/AuthenticateService";
import {AuthorizationManager} from "../../service/authorizationmanager";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  loginform: FormGroup;

  constructor(private fb: FormBuilder, private router: Router, private dialog: MatDialog,private as :AuthenticateService,private ut:AuthorizationManager) {

    this.loginform = this.fb.group({

      "username": new FormControl("", [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(10)
        ]
      ),

      "password": new FormControl("", Validators.required)

    });

    this.loginform.controls['username'].setValue("");
    this.loginform.controls['password'].setValue("");

  }

  authenticate(): void {

    let username = this.loginform.controls["username"].value;
    let password = this.loginform.controls["password"].value;

    this.as.post(username,password)
      .then((response: any) => {
        // console.log("PPPPP-"+response);
        // console.log(response.headers);
        let token = response.headers.get('Authorization');
        //console.log("AAAA-"+token);
        localStorage.setItem('Authorization', token);
        this.router.navigateByUrl("main/home");
        this.ut.getAuth(username);

      })
      .catch((error) => {
        console.log("BBBB");
        const dialogRef = this.dialog.open(MessageComponent, {
          width: '500px',
          data: {
            heading: "Invalid Login Details",
            message: "Username/Password Empty or Invalid. Check for Username Length"
          }
        });

        dialogRef.afterClosed().subscribe(async result => {
          if (!result) {
            return;
          }
        });

        this.router.navigateByUrl("login");

      });


  }

  signup(): void {

    const dialogRef = this.dialog.open(MessageComponent, {
      width: '500px',
      data: {heading: "Sign Up Not Available", message: "Public Registration Not Allowed. Please Contact System Admin"}
    });

    dialogRef.afterClosed().subscribe(async result => {
      if (!result) {
        return;
      }
    });

  }

  ngOnInit(): void {
    // this.ut.initializeMenuState();
  }

  // authenticate(): void {
  //   if (this.loginform.controls["username"].value == "admin" && this.loginform.controls["password"].value == "1234")
  //     this.router.navigateByUrl("main/home");
  //   else {
  //     const dialogRef = this.dialog.open(MessageComponent, {
  //       width: '500px',
  //       data: {
  //         heading: "Invalid Login Details",
  //         message: "Username/Password Empty or Inavlid. Check for Username Length"
  //       }
  //     });
  //
  //     dialogRef.afterClosed().subscribe(async result => {
  //       if (!result) {
  //         return;
  //       }
  //     });
  //
  //     this.router.navigateByUrl("login");
  //   }
  // }

}
