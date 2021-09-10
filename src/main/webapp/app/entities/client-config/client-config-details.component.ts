import { Component, Vue, Inject } from 'vue-property-decorator';

import { IClientConfig } from '@/shared/model/client-config.model';
import ClientConfigService from './client-config.service';

@Component
export default class ClientConfigDetails extends Vue {
  @Inject('clientConfigService') private clientConfigService: () => ClientConfigService;
  public clientConfig: IClientConfig = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.clientConfigId) {
        vm.retrieveClientConfig(to.params.clientConfigId);
      }
    });
  }

  public retrieveClientConfig(clientConfigId) {
    this.clientConfigService()
      .find(clientConfigId)
      .then(res => {
        this.clientConfig = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
