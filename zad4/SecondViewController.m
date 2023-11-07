#import "SecondViewController.h"

@interface SecondViewController ()

@end

@implementation SecondViewController

- (void) viewDidLoad {
    self.modifiedSurnameTextField.text = self.surname;
}

- (IBAction) goBack {
    NSString *itemToPassBack = self.modifiedSurnameTextField.text;
    [self.delegate addItemViewController:self didFinishEnteringItem:itemToPassBack];
    [self dismissViewControllerAnimated:YES completion:nil];
}

@end
