package lt.andro.rpi3dprinter

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), SupportedThings, MessageView {

    override fun showMessage(msg: String) {
        mainMessageView.text = msg
    }

    private val presenter: MainPresenter by lazy { MainPresenterImpl(this) }
    private val things: SupportedThings by lazy {
        if (AndroidThingsUtils().isThingsAvailable())
            ThingsExecutorImpl(this)
        else
            ThingsExecutorMock(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach()
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    override fun switchLed(isOn: Boolean) {
        things.switchLed(isOn)
    }

    override fun isLedOn(): Boolean {
        return things.isLedOn()
    }

    override fun switchRelay(isOn: Boolean) {
        things.switchRelay(isOn)
    }

    override fun isRelayOn(): Boolean {
        return things.isRelayOn()
    }
}

