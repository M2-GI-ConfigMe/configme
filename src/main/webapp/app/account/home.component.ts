import Component from 'vue-class-component';
import { Inject, Vue, Watch, Prop } from 'vue-property-decorator';

@Component({})
export default class AccountHome extends Vue {
  public routes = [
    { title: 'Mes Commandes', icon: 'mdi-cart', route: 'orders' },
    { title: 'Mes Informations', icon: 'mdi-cog', route: 'settings' },
  ];

  created() {
    console.log(this.$store.getters.account);
  }
  public get route() {
    const path = this.$route.path;
    let routeIdx: number;
    this.routes.forEach((r, i) => {
      if ('/account/' + r.route == path) routeIdx = i;
    });
    return routeIdx;
  }

  public set route(r) {
    this.$router.push(this.routes[r].route);
  }

  public get avatarName() {
    return this.$store.getters.account.firstName[0] + ' ' + this.$store.getters.account.lastName[0];
  }

  public get username() {
    return this.$store.getters.account.firstName + ' ' + this.$store.getters.account.lastName;
  }
}
