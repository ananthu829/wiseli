package uk.ac.tees.mad.w9501736.ui;



/*public class WiseLiViewModelFactory implements ViewModelProvider.Factory {

    private final FolioRepository folioRepository;
    private final ShipConnectivityLiveData shipConnectivityLiveData;
    private final FolioMetricsAnalytics folioMetricsAnalytics;

    public WiseLiViewModelFactory(FolioRepository folioRepository,
                                 ShipConnectivityLiveData shipConnectivityLiveData,
                                 FolioMetricsAnalytics folioMetricsAnalytics) {
        this.folioRepository = folioRepository;
        this.shipConnectivityLiveData = shipConnectivityLiveData;
        this.folioMetricsAnalytics = folioMetricsAnalytics;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (FolioViewModel.class.isAssignableFrom(modelClass)) {
            return (T) new FolioViewModel(folioRepository, shipConnectivityLiveData, folioMetricsAnalytics);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}*/
