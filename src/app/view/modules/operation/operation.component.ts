import {Component, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Role} from "../../../entity/role";
import {Module} from "../../../entity/module";
import {Opetype} from "../../../entity/opetype";
import {Operation} from "../../../entity/operation";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {UiAssist} from "../../../util/ui/ui.assist";
import {Roleservice} from "../../../service/roleservice";
import {Moduleservice} from "../../../service/moduleservice";
import {Opetypeservice} from "../../../service/opetypeservice";
import {Operationservice} from "../../../service/operationservice";
import {MatDialog} from "@angular/material/dialog";
import {AuthorizationManager} from "../../../service/authorizationmanager";
import {ConfirmComponent} from "../../../util/dialog/confirm/confirm.component";
import {MessageComponent} from "../../../util/dialog/message/message.component";

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.css']
})
export class OperationComponent {
  form!:FormGroup;
  ssearch!:FormGroup;

  roles!:Array<Role>;
  modules!:Array<Module>;
  operations!:Array<Operation>;
  opetypes!:Array<Opetype>;

  operation!:Operation;
  oldoperation!:Operation;

  columns: string[] = ['operation', 'module','opetype'];
  headers: string[] = ['Operation','Module','Operation Type'];
  binders: string[] = ['name','module.name','opetype.name'];

  data!:MatTableDataSource<Operation>
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  uiassist: UiAssist;

  imageurl: string = '';

  enaadd:boolean = false;
  enaupd:boolean = false;
  enadel:boolean = false;

  selectedrow: any;

  constructor(
    private fb:FormBuilder,
    private rs:Roleservice,
    private ms:Moduleservice,
    private os:Operationservice,
    private ot:Opetypeservice,
    private dg:MatDialog,
    public authService:AuthorizationManager
  ) {

    this.uiassist = new UiAssist(this);
    this.operations = new Array<Operation>();

    this.form = this.fb.group({
      "module":new FormControl('',Validators.required),
      "name":new FormControl('',Validators.required),
      "opetype":new FormControl('',Validators.required),
    }, {updateOn: 'change'});

    this.ssearch = this.fb.group({
      "ssmodule":new FormControl(),
      "ssoperation":new FormControl(),
    });

  }



  ngOnInit() {
    this.initialize();
  }


  initialize() {

    this.createView();

    this.rs.getAllList().then((rls:Role[])=>{
      this.roles = rls;
    });

    // this.os.getAllList().then((ops:Operation[])=>{
    //   this.operations = ops;
    // });

    this.ot.getAllList().then((optps:Opetype[])=>{
      this.opetypes = optps;
    });

    this.ms.getAllList().then((mds:Module[])=>{
      this.modules = mds;
    });

    this.createForm();

  }



  createForm() {

    this.form.controls['module'].setValidators([Validators.required]);
    this.form.controls['name'].setValidators([Validators.required]);
    this.form.controls['opetype'].setValidators([Validators.required]);

    Object.values(this.form.controls).forEach( control => { control.markAsTouched(); } );

    for (const controlName in this.form.controls) {
      const control = this.form.controls[controlName];
      control.valueChanges.subscribe(value => {

          if (this.oldoperation != undefined && control.valid) {
            // @ts-ignore
            if (value === this.operation[controlName]) {
              control.markAsPristine();
            } else {
              control.markAsDirty();
            }
          } else {
            control.markAsPristine();
          }
        }
      );

    }

    this.enableButtons(true,false,false);

  }

  enableButtons(add:boolean, upd:boolean, del:boolean){
    this.enaadd=add;
    this.enaupd=upd;
    this.enadel=del;
  }

  createView() {
    this.imageurl = 'assets/pending.gif';
    this.loadTable("");
  }


  loadTable(query:string):void{

    this.os.getAll(query)
      .then((oprns: Operation[]) => {
        this.operations = oprns;
        this.imageurl = 'assets/fullfilled.png';
      })
      .catch((error) => {
        console.log(error);
        this.imageurl = 'assets/rejected.png';
      })
      .finally(() => {
        this.data = new MatTableDataSource(this.operations);
        this.data.paginator = this.paginator;
      });

  }


  btnSearchMc(): void {

    const sserchdata = this.ssearch.getRawValue();

    let roleid = sserchdata.ssrole;
    let moduleid = sserchdata.ssmodule;
    let operationid = sserchdata.ssoperation;

    let query = "";

    if (roleid != null) query = query + "&roleid=" + roleid;
    if (moduleid != null) query = query + "&moduleid=" + moduleid;
    if (operationid != null) query = query + "&operationid=" + operationid;

    if (query != "") query = query.replace(/^./, "?")

    this.loadTable(query);

  }



  btnSearchClearMc(): void {

    const confirm = this.dg.open(ConfirmComponent, {
      width: '500px',
      data: {heading: "Search Clear", message: "Are you sure to Clear the Search?"}
    });

    confirm.afterClosed().subscribe(async result => {
      if (result) {
        this.ssearch.reset();
        this.loadTable("");
      }
    });

  }

  add() {

    let errors = this.getErrors();

    if (errors != "") {
      const errmsg = this.dg.open(MessageComponent, {
        width: '500px',
        data: {heading: "Errors - Operation Add ", message: "You have following Errors <br> " + errors}
      });
      errmsg.afterClosed().subscribe(async result => {
        if (!result) {
          return;
        }
      });
    } else {

      this.operation = this.form.getRawValue();

      let prvdata: string = "";

      prvdata = prvdata + "<br>Operation is : " + this.form.controls['name'].value;
      prvdata = prvdata + "<br>Module is : " + this.operation.module.name;

      const confirm = this.dg.open(ConfirmComponent, {
        width: '500px',
        data: {
          heading: "Confirmation - Operation Add",
          message: "Are you sure to Add the folowing Operation? <br> <br>" + prvdata
        }
      });

      let addstatus: boolean = false;
      let addmessage: string = "Server Not Found";

      confirm.afterClosed().subscribe(async result => {
        if (result) {
          // console.log("EmployeeService.add(emp)");

          this.os.add(this.operation).then((responce: [] | undefined) => {
            //console.log("Res-" + responce);
            //console.log("Un-" + responce == undefined);
            if (responce != undefined) { // @ts-ignore
              console.log("Add-" + responce['id'] + "-" + responce['url'] + "-" + (responce['errors'] == ""));
              // @ts-ignore
              addstatus = responce['errors'] == "";
              console.log("Add Sta-" + addstatus);
              if (!addstatus) { // @ts-ignore
                addmessage = responce['errors'];
              }
            } else {
              console.log("undefined");
              addstatus = false;
              addmessage = "Content Not Found"
            }
          }).finally(() => {

            if (addstatus) {
              addmessage = "Successfully Saved";
              this.form.reset();
              Object.values(this.form.controls).forEach(control => {
                control.markAsTouched();
              });
              this.loadTable("");
            }

            const stsmsg = this.dg.open(MessageComponent, {
              width: '500px',
              data: {heading: "Status -Operation Add", message: addmessage}
            });

            stsmsg.afterClosed().subscribe(async result => {
              if (!result) {
                return;
              }
            });
          });
        }
      });
    }
  }



  getErrors(): string {

    let errors: string = "";
    for (const controlName in this.form.controls) {
      const control = this.form.controls[controlName];
      if (control.errors)
        errors = errors + "<br>Invalid " + controlName;
    }
    return errors;

  }


  fillForm(operation: Operation) {

    this.enableButtons(false,true,true);

    this.selectedrow= operation;

    console.log(JSON.stringify(this.selectedrow));

    this.operation = JSON.parse(JSON.stringify(operation));
    this.oldoperation = JSON.parse(JSON.stringify(operation));

    //@ts-ignore
    this.operation.opetype = this.opetypes.find(o => o.id === this.operation.opetype.id);
    //@ts-ignore
    this.operation.module = this.modules.find(m => m.id === this.operation.module.id);

    this.form.patchValue(this.operation);
    this.form.markAsPristine();

  }



  getUpdates(): string {
    let updates: string = "";
    for (const controlName in this.form.controls) {
      const control = this.form.controls[controlName];
      if (control.dirty) {
        updates = updates + "<br>" + controlName.charAt(0).toUpperCase() + controlName.slice(1)+" Changed";
      }
    }
    return updates;
  }

  update() {

    let errors = this.getErrors();

    if (errors != "") {

      const errmsg = this.dg.open(MessageComponent, {
        width: '500px',
        data: {heading: "Errors - Operation Update ", message: "You have following Errors <br> " + errors}
      });
      errmsg.afterClosed().subscribe(async result => { if (!result) { return; } });

    } else {

      let updates: string = this.getUpdates();

      if (updates != "") {

        let updstatus: boolean = false;
        let updmessage: string = "Server Not Found";

        const confirm = this.dg.open(ConfirmComponent, {
          width: '500px',
          data: {
            heading: "Confirmation - Operation Update",
            message: "Are you sure to Save folowing Updates? <br> <br>" + updates
          }
        });
        confirm.afterClosed().subscribe(async result => {
          if (result) {
            //console.log("EmployeeService.update()");
            this.operation = this.form.getRawValue();

            this.operation.id = this.oldoperation.id;

            this.os.update(this.operation).then((responce: [] | undefined) => {
              //console.log("Res-" + responce);
              // console.log("Un-" + responce == undefined);
              if (responce != undefined) { // @ts-ignore
                //console.log("Add-" + responce['id'] + "-" + responce['url'] + "-" + (responce['errors'] == ""));
                // @ts-ignore
                updstatus = responce['errors'] == "";
                //console.log("Upd Sta-" + updstatus);
                if (!updstatus) { // @ts-ignore
                  updmessage = responce['errors'];
                }
              } else {
                //console.log("undefined");
                updstatus = false;
                updmessage = "Content Not Found"
              }
            }).finally(() => {
              if (updstatus) {
                updmessage = "Successfully Updated";
                this.form.reset();
                Object.values(this.form.controls).forEach(control => { control.markAsTouched(); });
                this.loadTable("");
              }

              const stsmsg = this.dg.open(MessageComponent, {
                width: '500px',
                data: {heading: "Status -Operation Update", message: updmessage}
              });
              stsmsg.afterClosed().subscribe(async result => { if (!result) { return; } });

            });
          }
        });
      }
      else {

        const updmsg = this.dg.open(MessageComponent, {
          width: '500px',
          data: {heading: "Confirmation - Operation Update", message: "Nothing Changed"}
        });
        updmsg.afterClosed().subscribe(async result => { if (!result) { return; } });

      }
    }

  }


  delete() {

    const confirm = this.dg.open(ConfirmComponent, {
      width: '500px',
      data: {
        heading: "Confirmation - Operation Delete",
        message: "Are you sure to Delete folowing Authority? <br> <br>" + this.operation
      }
    });

    confirm.afterClosed().subscribe(async result => {
      if (result) {
        let delstatus: boolean = false;
        let delmessage: string = "Server Not Found";

        this.os.delete(this.operation.id).then((responce: [] | undefined) => {

          if (responce != undefined) { // @ts-ignore
            delstatus = responce['errors'] == "";
            if (!delstatus) { // @ts-ignore
              delmessage = responce['errors'];
            }
          } else {
            delstatus = false;
            delmessage = "Content Not Found"
          }
        } ).finally(() => {
          if (delstatus) {
            delmessage = "Successfully Deleted";
            this.form.reset();
            Object.values(this.form.controls).forEach(control => { control.markAsTouched(); });
            this.loadTable("");
          }

          const stsmsg = this.dg.open(MessageComponent, {
            width: '500px',
            data: {heading: "Status - Operation Delete ", message: delmessage}
          });
          stsmsg.afterClosed().subscribe(async result => { if (!result) { return; } });

        });
      }
    });
  }
}
