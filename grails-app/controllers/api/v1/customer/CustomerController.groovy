package api.v1.customer

class CustomerController  {//extends RestfulController<CustomerProfileResponse> {

//    static namespace='v1'
//
//    private static final Gson gson = new Gson()
//
//    def customerService
//
//    CustomerController() {
//        super(CustomerProfileResponse)
//    }
//
//    @Override
//    Object show() {
//
//        String brandId = params?.brandId ?: 'FDM'
//        String customerNumber = params.id
//        Boolean forceClear = params.getBoolean('forceClear') || params.getBoolean('refresh')
//
//        CustomerProfileResponse customerProfileResponse = customerService.getCustomer(brandId, customerNumber, forceClear)
//        render text: gson.toJson(customerProfileResponse), contentType: "application/json"
//    }
//
//    @Override
//    Object create() {
//        renderClientErrorMessage('Create Operation Not Supported.')
//    }
//
//    @Override
//    Object save() {
//        renderClientErrorMessage('Save Operation Not Supported.')
//    }
//
//    @Override
//    Object edit() {
//        renderClientErrorMessage('Edit Operation Not Supported.')
//    }
//
//    @Override
//    Object delete() {
//        renderClientErrorMessage('Delete Operation Not Supported.')
//    }
//
//    @Override
//    Object update() {
//        renderClientErrorMessage('Update Operation Not Supported.')
//    }
//
//    @Override
//    Object index() {
//        show()
//    }
//
//    private Object renderClientErrorMessage(String errorMessage) {
//        CustomerProfileResponse customerProfileResponse = new CustomerProfileResponse(successFlag: false,
//                messages: new ErrorBase(
//                        messagesCollection: new ArrayOfWebServiceError(
//                                webServiceError: [new WebServiceError(errorMessage: errorMessage)])
//                )
//        )
//
//        render text: gson.toJson(customerProfileResponse), contentType: "application/json"
//    }

}
