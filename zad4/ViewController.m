//
//  ViewController.m
//  Zadanko4
//
//  Created by Modzel on 12/10/2021.
//

#import "ViewController.h"
#import "SecondViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void) viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (IBAction) enter {
    NSString *myName = @"Kamil";
    NSString *yourName = self.nameTextField.text;
    
    if ([yourName length] == 0) {
        yourName = @"World";
    }
    
    NSString *message;
    if ([yourName isEqualToString:myName]) {
        message = [NSString stringWithFormat:@"Hello %@! We have the same name ;)", yourName];
    } else {
        message = [NSString stringWithFormat:@"Hello %@!", yourName];
    }
    
    self.messageLabel.text = message;
}

- (void) prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([segue.identifier isEqualToString:@"sendSurnameSegue"]) {
        SecondViewController *destinationController = (SecondViewController *) segue.destinationViewController;

        destinationController.surname = self.surnameTextField.text;
        destinationController.delegate = self;
    }
}

- (void) addItemViewController:(SecondViewController *) controller didFinishEnteringItem:(NSString *) item {
    NSLog(@"This was returned from SecondViewController %@", item);
    self.surnameTextField.text = item;
}

@end
